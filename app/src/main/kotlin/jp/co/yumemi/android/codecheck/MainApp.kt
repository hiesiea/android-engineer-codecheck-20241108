package jp.co.yumemi.android.codecheck

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import jp.co.yumemi.android.codecheck.core.data.model.RepositoryItem
import jp.co.yumemi.android.codecheck.feature.detail.DetailRoute
import jp.co.yumemi.android.codecheck.feature.detail.RepositoryDetailScreen
import jp.co.yumemi.android.codecheck.feature.search.RepositorySearchScreen
import jp.co.yumemi.android.codecheck.feature.search.SearchRoute

@Composable
fun MainApp() {
    val navController = rememberNavController()
    MainNavHost(navController = navController)
}

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = SearchRoute) {
        composable<SearchRoute> {
            RepositorySearchScreen(
                onItemClick = {
                    navController.navigate(
                        DetailRoute(
                            name = it.name,
                            ownerIconUrl = it.ownerIconUrl,
                            htmlUrl = it.htmlUrl,
                            language = it.language,
                            stargazersCount = it.stargazersCount,
                            watchersCount = it.watchersCount,
                            forksCount = it.forksCount,
                            openIssuesCount = it.openIssuesCount,
                        ),
                    )
                },
            )
        }
        composable<DetailRoute> {
            val detailRoute = it.toRoute<DetailRoute>()
            RepositoryDetailScreen(
                item = RepositoryItem(
                    name = detailRoute.name,
                    ownerIconUrl = detailRoute.ownerIconUrl,
                    htmlUrl = detailRoute.htmlUrl,
                    language = detailRoute.language,
                    stargazersCount = detailRoute.stargazersCount,
                    watchersCount = detailRoute.watchersCount,
                    forksCount = detailRoute.forksCount,
                    openIssuesCount = detailRoute.openIssuesCount,
                ),
                onCloseButtonClick = {
                    navController.navigateUp()
                },
                onShowDetailButtonClick = {
                    val intent = CustomTabsIntent.Builder().build()
                    intent.launchUrl(context, Uri.parse(detailRoute.htmlUrl))
                },
            )
        }
    }
}
