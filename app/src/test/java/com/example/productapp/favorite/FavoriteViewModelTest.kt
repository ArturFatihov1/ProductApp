package com.example.productapp.favorite

import com.example.productapp.favorite.data.FavoriteRepository
import com.example.productapp.favorite.presentation.FavoriteUiState
import com.example.productapp.favorite.presentation.FavoriteViewModel
import com.example.productapp.product.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class DetailViewModelTest {
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var repository: FakeRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var observable: FakeDetailUiObservable

    @Before
    fun setup() {
        repository = FakeRepository()
        runAsync = FakeRunAsync()
        observable = FakeDetailUiObservable.Base()
        viewModel = FavoriteViewModel(
            repository = repository,
            runAsync = runAsync,
            observable = observable,
            clearViewModel = FakeClearViewModel()
        )
    }

    @Test
    fun check_detail_recipe() {
        viewModel.init()
        runAsync.returnResult()
        var actual: FavoriteUiState = observable.postUiStateCalledList.last()
        var expected: FavoriteUiState = FavoriteUiState.Initial(
            id = "1",
            title = "Essence Mascara Lash Princess",
            url = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp",
            price = 9.99,
        )
        assertEquals(expected, actual)

        viewModel.like()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = FavoriteUiState.RecipeLikeState
        assertEquals(expected, actual)

        viewModel.unLike()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = FavoriteUiState.RecipeUnLikeState
        assertEquals(expected, actual)

        viewModel.back()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = FavoriteUiState.Leave
        assertEquals(expected, actual)
        assertEquals(true, repository.clearCalled)

        viewModel.detailLoad()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = FavoriteUiState.DetailLoad
        assertEquals(expected, actual)
        assertEquals(true, repository.clearCalled)
    }
}

private class FakeRepository : FavoriteRepository {
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

    override suspend fun product(): Product {
        return list[index]
    }

    override fun likeProduct() {
        like = true
    }

    override fun unLikeProduct() {
        like = false
    }

    override fun like() = like

    var clearCalled = false

    override suspend fun clear() {
        clearCalled = true
    }

}

private interface FakeFavoriteUiObservable : FakeUiObservable<FavoriteUiState>,
    FavoriteUiObservable {
    class Base : FakeUiObservable.Abstract<FavoriteUiState>(), FakeFavoriteUiObservable
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
