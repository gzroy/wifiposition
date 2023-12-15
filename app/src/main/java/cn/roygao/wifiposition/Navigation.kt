package cn.roygao.wifiposition

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cn.roygao.wifiposition.Destinations.MEASURE_ROUTE
import cn.roygao.wifiposition.Destinations.REPORT_ROUTE

object Destinations {
    const val MEASURE_ROUTE = "measure"
    const val REPORT_ROUTE = "report/{positionName}/{angle}"
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MEASURE_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MEASURE_ROUTE) {
            MeasureScreen(
                navController = navController
                //onNavigateToReport = {navController.navigate(REPORT_ROUTE)}
            )
        }
        composable(
            REPORT_ROUTE,
            arguments = listOf(
                navArgument("positionName") {type = NavType.StringType},
                navArgument("angle") {type = NavType.FloatType}
            )
        ) { backStackEntry ->
            val positionName = backStackEntry.arguments?.getString("positionName")
            val angle = backStackEntry.arguments?.getFloat("angle")
            WifiMeasureReport(positionName, angle)
        }
    }
}
