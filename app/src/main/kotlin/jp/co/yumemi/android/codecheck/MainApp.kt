package jp.co.yumemi.android.codecheck

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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
    NavHost(navController = navController, startDestination = SearchRoute) {
    }
}
