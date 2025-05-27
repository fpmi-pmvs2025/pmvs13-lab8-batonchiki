package com.example.pharmacyapp.data.repository

import android.util.Log
import com.example.pharmacyapp.data.local.ProductDao
import com.example.pharmacyapp.data.local.ProductEntity
import com.example.pharmacyapp.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val apiService: ApiService
) : ProductRepository {

    private val productsJsonUrl = "https://gist.githubusercontent.com/RustambekTalipov/423e2d0ac9ade2f20c345eebe2862e00/raw/78fe5405a707c6531e59c2e37fe7230782f2ecce/pharmacy_products.json"

    override fun getAllProducts(): Flow<List<ProductEntity>> {
        Log.d("ProductRepositoryImpl", "Getting all products flow from DAO")
        return productDao.getAllProducts()
    }

    override fun getFavoriteProducts(): Flow<List<ProductEntity>> {
        Log.d("ProductRepositoryImpl", "Getting favorite products flow from DAO")
        return productDao.getFavoriteProducts()
    }

    override fun getProductById(productId: Long): Flow<ProductEntity?> {
        Log.d("ProductRepositoryImpl", "Getting product by ID $productId flow from DAO")
        return productDao.getProductById(productId)
    }

    override fun searchProducts(query: String): Flow<List<ProductEntity>> {
        Log.d("ProductRepositoryImpl", "Searching products with query '$query' flow from DAO")
        return productDao.searchProducts(query)
    }

    override suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean) {
        Log.d("ProductRepositoryImpl", "Updating favorite status for $productId to $isFavorite")
        withContext(Dispatchers.IO) {
            productDao.updateFavoriteStatus(productId, isFavorite)
        }
    }

    override suspend fun refreshProducts() {
        withContext(Dispatchers.IO) {
            try {
                Log.i("ProductRepositoryImpl", "Attempting to fetch products from: $productsJsonUrl")

                val response = apiService.getProductsFromUrl(productsJsonUrl)

                if (response.isSuccessful) {
                    val productList = response.body()
                    if (productList != null) {
                        if (productList.isNotEmpty()) {
                            productDao.insertAll(productList)
                            Log.i("ProductRepositoryImpl", "Successfully fetched and saved ${productList.size} products to DB.")
                        } else {
                            Log.w("ProductRepositoryImpl", "Fetched product list is empty. No changes made to DB.")
                        }
                    } else {
                        Log.w("ProductRepositoryImpl", "Network request successful, but response body was null.")
                        throw IOException("Received null response body")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown HTTP error"
                    Log.e("ProductRepositoryImpl", "Network request failed with code ${response.code()}: $errorBody")
                    throw HttpException(response)
                }
            } catch (e: IOException) {
                Log.e("ProductRepositoryImpl", "Network IO error during product refresh", e)
                throw e
            } catch (e: HttpException) {
                Log.e("ProductRepositoryImpl", "HTTP error during product refresh", e)
                throw e
            } catch (e: Exception) {
                Log.e("ProductRepositoryImpl", "Generic error during product refresh", e)
                throw e
            }
        }
    }
}