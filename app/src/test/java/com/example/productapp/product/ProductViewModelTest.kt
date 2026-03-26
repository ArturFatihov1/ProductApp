package com.example.productapp.product

import com.example.productapp.product.data.ProductRepository
import com.example.productapp.product.presentation.ProductListUiState
import com.example.productapp.product.presentation.ProductUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class ProductViewModelTest {
    private lateinit var viewModel: ProductViewModel
    private lateinit var repository: FakeRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var observable: FakeProductUiObservable

    @Before
    fun setup() {
        repository = FakeRepository()
        runAsync = FakeRunAsync()
        observable = FakeProductUiObservable.Base()
        viewModel = ProductViewModel(
            repository = repository,
            runAsync = runAsync,
            observable = observable,
            clearViewModel = FakeClearViewModel()
        )
    }

    @Test
    fun caseNumber1() {
        viewModel.init()
        runAsync.returnResult()
        var actual: ProductListUiState = observable.postUiStateCalledList.last()
        var expected: ProductListUiState = ProductListUiState.Initial(
            id = "1",
            title = "Essence Mascara Lash Princess",
            url = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp",
            price = 9.99,
            description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula."
        )
        assertEquals(expected, actual)

        viewModel.like(index = 0)
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = ProductListUiState.ProductCardLikeState(0)
        assertEquals(expected, actual)

        viewModel.unlike(index = 0)
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = ProductListUiState.ProductCardUnLikeState(0)
        assertEquals(expected, actual)

        viewModel.favoriteLoad()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = ProductListUiState.FavoriteLoad
        assertEquals(expected, actual)
        assertEquals(true, repository.clearCalled)

        viewModel.detailLoad()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = ProductListUiState.DetailLoad
        assertEquals(expected, actual)
        assertEquals(true, repository.clearCalled)
    }
}

private class FakeRepository : ProductRepository {
    private val list: List<Product> = listOf(
        Product(
            id = "1",
            title = "Essence Mascara Lash Princess",
            url = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp",
            price = 9.99,
            description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula."
        ),
        Product(
            id = "2",
            title = "Euse",
            url = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp",
            price = 15.99,
            description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula."
        )
    )

    private var index = 0
    private var like = false
    private var userChoiceIndex = -1

    override suspend fun product(): Product {
        return list[index]
    }

    override fun likeProduct() {
        like = true
    }

    override fun unLikeProduct() {
        like = false
    }

    override fun saveUserChoice(index: Int) {
        userChoiceIndex = index
    }

    override fun like() = like

    var clearCalled = false

    override suspend fun clear() {
        clearCalled = true
    }

}

data class Product(
    val id: String,
    val title: String,
    val url: String,
    val price: Double,
    val description: String,
)

private interface FakeProductUiObservable : FakeUiObservable<ProductUiState>, ProductlUiObservable {
    class Base : FakeUiObservable.Abstract<ProductUiState>(), FakeProductUiObservable
}

interface FakeUiObservable<T : Any> : UiObservable<T> {
    var registerCalledCount: Int
    var unregisterCalledCount: Int
    val postUiStateCalledList: MutableList<T>

    abstract class Abstract<T : Any> : FakeUiObservable<T> {

        private var uiStateCached: T? = null
        private var observerCached: ((T) -> Unit)? = null

        override var registerCalledCount: Int = 0
        override var unregisterCalledCount: Int = 0
        override val postUiStateCalledList: MutableList<T> = mutableListOf()

        override fun register(observer: (T) -> Unit) {
            registerCalledCount++
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached!!)
                uiStateCached = null
            }
        }

        override fun unregister() {
            unregisterCalledCount++
            observerCached = null
        }

        override fun postUiState(uiState: T) {
            postUiStateCalledList.add(uiState)
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached!!.invoke(uiState)
                uiStateCached = null
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class FakeRunAsync : RunAsync {
    private var result: Any? = null
    private var ui: (Any) -> Unit = {}

    override fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    ) = runBlocking {
        result = heavyOperation.invoke()
        ui = uiUpdate as (Any) -> Unit
    }

    fun returnResult() {
        ui.invoke(result!!)
    }
}

