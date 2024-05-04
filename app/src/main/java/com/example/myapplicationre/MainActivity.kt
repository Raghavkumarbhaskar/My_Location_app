package com.example.myapplicationre
import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplicationre.ui.theme.MyApplicationreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationreTheme {
                // A surface container using the 'background' color from the theme
                var viewm:view= viewModel()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                          myapp(viewm)
                    }

                }
            }
        }
    @Composable
    fun myapp(viewm:view){
        var context = LocalContext.current
        var locationunit = location(context)
        locatindispay(context = context, location = locationunit ,viewm)
    }
    @Composable
    fun locatindispay(context:Context,location:location,viewm: view)
    {
        var value =viewm.loca.value
        var readableadd  = value?.let {
            location(context).getReadableLocation(it)
        }

        val locationPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()


        ) { permissions ->
            if ((permissions[Manifest.permission.ACCESS_COARSE_LOCATION]) == true && (permissions[Manifest.permission.ACCESS_FINE_LOCATION]) == true) {
                location(context).requestlocationupdate(viewm)

            } else {
                val rationalerequest = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

                if (rationalerequest) {
                    Toast.makeText(context, "it is required", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "please enable it from setting", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            if(value != null)
            {
                Text(text = "location is ${readableadd}")
            }else{
                Text(text = "provide location of your")

            }

            Button(onClick = {
                if(location(context).haslocation())
            {
               location(context).requestlocationupdate(viewm)
            }
            else{
                locationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION))

            }}) {
                Text(text = "location")
            }
        }
    }
    }




