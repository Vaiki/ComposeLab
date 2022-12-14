package com.vaiki.composelab

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaiki.composelab.model.Hero
import com.vaiki.composelab.model.HeroesRepository
import com.vaiki.composelab.model.HeroesRepository.heroes
import com.vaiki.composelab.ui.theme.ComposeLabTheme
import com.vaiki.composelab.ui.theme.Shapes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                // A surface container using the 'background' color from the theme
                HeroesApp()
            }
        }
    }

    @Composable
    fun HeroesApp() {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = { TopAppBarHero() }) {
            HeroesList(Modifier.padding(it))
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun HeroesList(modifier: Modifier = Modifier) {
        //Анимация появления списка
        val visibleState = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                targetState = true
            }
        }
        // Fade in entry animation for the entire list
        AnimatedVisibility(
            visibleState = visibleState,
            enter = fadeIn(
                animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
            ),
            exit = fadeOut()
        ) {
            LazyColumn(modifier = Modifier.background(MaterialTheme.colors.background)) {
                itemsIndexed(heroes) { index, hero ->
                    HeroItem(
                        hero = hero,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            // Animate each list item to slide in vertically
                            .animateEnterExit(
                                enter = slideInVertically(
                                    animationSpec = spring(
                                        stiffness = StiffnessVeryLow,
                                        dampingRatio = DampingRatioLowBouncy
                                    ),
                                    initialOffsetY = { it * (index + 1) } // staggered entrance
                                )
                            )
                    )
                }
                //загрузка списка без анимации
                //items(heroes) {
                //  HeroItem(hero = it, Modifier.padding(16.dp, 8.dp))
                // }
            }
        }
    }
    @Composable
    fun TopAppBarHero() {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "SuperHeroes", style = MaterialTheme.typography.h1)
        }
    }
    @Composable
    fun HeroItem(hero: Hero, modifier: Modifier = Modifier) {
        Card(
            modifier = modifier,
            elevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .sizeIn(minHeight = 72.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = hero.nameRes),
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        text = stringResource(id = hero.descriptionRes),
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {

                    Image(
                        painter = painterResource(id = hero.imageRes),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
    @Preview("Light Theme")
    @Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun PreviewHeroes() {
        //HeroItem(hero = Hero(R.string.hero1, R.string.description1, R.drawable.android_superhero1))
        ComposeLabTheme {
            HeroesApp()
        }
    }
}