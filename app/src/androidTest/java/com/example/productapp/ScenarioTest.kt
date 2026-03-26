package com.example.productapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.productapp.FakeProducts.allProducts
import com.example.productapp.FakeProducts.firstProduct
import com.example.productapp.FakeProducts.searchedProducts
import com.example.productapp.detail.DetailPage
import com.example.productapp.favorite.FavoritePage
import com.example.productapp.product.ProductListPage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    private lateinit var productListPage: ProductListPage
    private lateinit var detailPage: DetailPage
    private lateinit var favoritePage: FavoritePage

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        productListPage = ProductListPage()
        detailPage = DetailPage(firstProduct)
        favoritePage = FavoritePage()
    }

    @Test
    fun get_products_at_start() {
        productListPage.assertLoadingState()
        productListPage.waitTillError()
        productListPage.assertErrorState()

        productListPage.clickRetry()
        productListPage.assertProductListState()
    }

    @Test
    fun get_products_search_product_add_favourites() {
        get_products_at_start()

        productListPage.assertProductListState()
        productListPage.assertInputEmptyState()

        productListPage.addInput("E")
        productListPage.addInput("s")

        productListPage = ProductListPage(searchedProducts("Es"))
        productListPage.assertProductListState()

        productListPage.clickLikeOnProduct(0)
        productListPage.assertFirstProductIsLiked()
    }

    @Test
    fun check_detail_recipe() {
        get_products_at_start()

        productListPage.clickFirstProduct()
        detailPage.assertDetailState()

        detailPage.clickBack()
        productListPage.assertProductListState()

        productListPage.clickFirstProduct()
        detailPage.assertDetailState()

        detailPage.clickOnLike()           // like
        detailPage.assertProductIsLiked()

        detailPage.clickBack()
        productListPage.assertFirstProductIsLiked()

        productListPage.clickFavoriteButton()

        favoritePage = FavoritePage(allProducts.take(1))
        favoritePage.assertFavoritesState()

        favoritePage.clickFirstRecipe()
        detailPage.assertDetailState()

        detailPage.clickOnLike()           // unlike
        detailPage.assertProductIsNotLiked()

        detailPage.clickBack()
        favoritePage.assertFavoritesEmptyState()
    }
}
data class Product(
    val id: String,
    val title: String,
    val url: String,
    val price: Double,
    val description: String,
)