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

    lateinit var productListPage: ProductListPage
    lateinit var detailPage: DetailPage
    lateinit var favoritePage: FavoritePage

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        productListPage = ProductListPage(products = allProducts)
        detailPage = DetailPage(product = firstProduct)
        favoritePage = FavoritePage(products = emptyList<Product>())
    }

    @Test
    fun get_products_at_start() {
        activityScenarioRule.doWithRecreate { productListPage.assertLoadingState() }

        productListPage.waitTillError()
        activityScenarioRule.doWithRecreate { productListPage.assertErrorState() }

        productListPage.clickRetry()
        activityScenarioRule.doWithRecreate { productListPage.assertProductListState() }
    }

    @Test
    fun get_products_search_product_add_favourites() {
        get_products_at_start()
        activityScenarioRule.doWithRecreate {
            productListPage.assertProductListState()
            productListPage.assertInputEmptyState()
        }

        productListPage.addInput(text = "E")
        activityScenarioRule.doWithRecreate(productListPage::assertInputSufficientState)

        productListPage.addInput(text = "s")
        activityScenarioRule.doWithRecreate(productListPage::assertInputSufficientState)

        productListPage = ProductListPage(searchedProducts("Es"))
        activityScenarioRule.doWithRecreate {
            productListPage.assertRecipeListChanged()
            productListPage.assertInputSufficientState()
        }

        productListPage.clickLikeOnProduct(0)
        activityScenarioRule.doWithRecreate(productListPage::assertFirstProductIsLiked)
    }

    @Test
    fun check_detail_recipe() {
        get_products_at_start()
        productListPage.clickFirstProduct()

        activityScenarioRule.doWithRecreate {
            detailPage.assertDetailState()
        }

        detailPage.clickBack()
        activityScenarioRule.doWithRecreate {
            productListPage.assertProductListState()
            productListPage.assertInputEmptyState()
        }
        productListPage.clickFirstProduct()

        activityScenarioRule.doWithRecreate {
            detailPage.assertDetailState()
        }

        detailPage.clickOnLike() // like
        activityScenarioRule.doWithRecreate {
            detailPage.assertDetailState()
            detailPage.assertProductIsLiked()
        }

        detailPage.clickBack()
        activityScenarioRule.doWithRecreate(productListPage::assertFirstProductIsLiked)

        productListPage.clickFavoriteButton()
        favoritePage = FavoritePage(products = allProducts.take(1))
        activityScenarioRule.doWithRecreate {
            favoritePage.assertFavoritesState()
        }

        favoritePage.clickFirstRecipe()
        activityScenarioRule.doWithRecreate {
            detailPage.assertDetailState()
        }

        detailPage.clickOnLike() //unlike
        activityScenarioRule.doWithRecreate {
            detailPage.assertDetailState()
            detailPage.assertProductsIsNotLiked()
        }

        detailPage.clickBack()
        activityScenarioRule.doWithRecreate(favoritePage::assertFavoritesEmptyState)
    }

    private fun ActivityScenarioRule<*>.doWithRecreate(block: () -> Unit) {
        block.invoke()
        this.scenario.recreate()
        block.invoke()
    }
}

data class Product(
    val id: String,
    val title: String,
    val url: String,
    val price: Double,
    val description: String,
)
