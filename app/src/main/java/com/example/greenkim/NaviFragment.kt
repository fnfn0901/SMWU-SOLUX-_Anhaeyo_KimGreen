package com.example.greenkim

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.greenkim.data.BottomNavigationItem
import com.example.greenkim.databinding.FragmentNaviBinding
import com.example.greenkim.ui.theme.GreenKimTheme

class NaviFragment : Fragment() {

    //private lateinit var languageButton: ImageButton
    private var _binding: FragmentNaviBinding? = null
    private val binding get() = _binding!!

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNaviBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeNavi.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                GreenKimTheme {
                    var context = LocalContext.current
                    val items = listOf(
                        BottomNavigationItem(
                            title = "Home",
                            selectedIcon = Icons.Filled.Home,
                            unselectedIcon = Icons.Outlined.Home,
                            hasNews = false,
                        ),

                        BottomNavigationItem(
                            title = "Community",
                            selectedIcon = Icons.Filled.Language,
                            unselectedIcon = Icons.Outlined.Language,
                            hasNews = false,
                        ),
                        BottomNavigationItem(
                            title = "MyPage",
                            selectedIcon = Icons.Filled.AccountCircle,
                            unselectedIcon = Icons.Outlined.AccountCircle,
                            hasNews = true,
                        ),
                    )
                    var selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }
                    val navController = rememberNavController()
                    Scaffold(
                        //containerColor = Color.White,
                        bottomBar = {
                            NavigationBar {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(item.title) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (item.badgeCount != null) {
                                                        Badge {
                                                            Text(item.badgeCount.toString())
                                                        }
                                                    } else if (item.hasNews) {
                                                        Badge()
                                                    }
                                                }) {
                                                Icon(
                                                    imageVector = if (index == selectedItemIndex) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon,
                                                    contentDescription = item.title
                                                )
                                            }
                                        })
                                }
                            }

                        }) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "Home",
                            Modifier.padding(innerPadding)

                        ) {
                            composable("Home") {}
                            composable("Community") {
                            }
                            composable("MyPage") { }
                        }

                    }
                }
            }
        }

        return view
    }
}