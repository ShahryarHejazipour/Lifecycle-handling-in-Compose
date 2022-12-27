package com.tispunshahryar960103.lifecyclehandlingincompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tispunshahryar960103.lifecyclehandlingincompose.ui.theme.LifeCycleHandlingInComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LifeCycleHandlingInComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                  //  Greeting("Android")
                    MainScreen()
                }
            }
        }
    }
}

//To seeing if Lifecycle change occur or not -> For test

@Composable
fun MainScreen() {

    ComposableLifeCycle{lifecycleOwner, event ->

        when(event){
            Lifecycle.Event.ON_CREATE -> { Log.d("TAG", "MainScreen: ON_CREATE")}
            Lifecycle.Event.ON_START -> { Log.d("TAG", "MainScreen: ON_START")}
            Lifecycle.Event.ON_RESUME -> { Log.d("TAG", "MainScreen: ON_RESUME")}
            Lifecycle.Event.ON_PAUSE -> { Log.d("TAG", "MainScreen: ON_PAUSE")}
            Lifecycle.Event.ON_STOP -> { Log.d("TAG", "MainScreen: ON_STOP")}
            Lifecycle.Event.ON_DESTROY -> { Log.d("TAG", "MainScreen: ON_DESTROY")}
            Lifecycle.Event.ON_ANY -> { Log.d("TAG", "MainScreen: ON_ANY")}


        }


    }


}


@Composable
fun ComposableLifeCycle(
     lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner,Lifecycle.Event) -> Unit,
) {

    DisposableEffect(lifecycleOwner){

        val observer = LifecycleEventObserver{ lifecycleOwner, event ->

            onEvent(lifecycleOwner,event)
        }

        // attach observer to LifecycleOwner

        lifecycleOwner.lifecycle.addObserver(observer)

        //when "DisposableEffect" is destroyed for having no memory leak using "onDispose"
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }


    }


}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LifeCycleHandlingInComposeTheme {
        Greeting("Android")
    }
}