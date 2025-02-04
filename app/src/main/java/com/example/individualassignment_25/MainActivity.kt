package com.example.individualassignment_25

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.individualassignment_25.ui.theme.IndividualAssignment_25Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndividualAssignment_25Theme {
                makeScreen()
            }
        }
    }
}

//Make an entry on the shopping list
@Composable
fun cartItem(image: Int, name: String, quant: Int, price: Double) {
    val fsize = 22.sp
    //create a row of item info followed by a divider line
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 128.dp)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = name,
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                    )
                    .size(80.dp)
            )
            Spacer(
                Modifier.size(12.dp)
            )
            Text(
                text = name,
                fontSize = fsize,
                color = Color.Black,
                modifier = Modifier.width(width = 150.dp)
            )
            Spacer(
                Modifier.size(6.dp)
            )
            Text(
                text = "(" + quant + ")"
            )
            Spacer(
                Modifier.size(12.dp)
            )
            Text(
                text = "$" + price,
                fontSize = fsize,
                color = Color.DarkGray,
                fontStyle = FontStyle.Italic
            )
        }
        Divider(color = Color.Blue,
            modifier = Modifier.height(height = 6.dp))
    }
}

//Make checkout button. Button takes coroutine scope and snackbar host to activate snackbar.
@Composable
fun checkoutButton(scope: CoroutineScope, snackbarHost: SnackbarHostState) {
    var ordered =  remember { mutableStateOf(false) }
    val buttonText = if (ordered.value) "Ordered" else "Order"
    Button(
        onClick = {
            scope.launch {
                snackbarHost.showSnackbar(
                    message = "Ordered"
                )
            }
            if (!ordered.value) {
                ordered.value = true
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(ordered.value) Color.Gray else Color.Blue),
        modifier = Modifier
            .height(height = 70.dp)
            .fillMaxWidth()
            .clip(shape = CircleShape)
            .padding(horizontal = 48.dp)
    ) {
        Text(
            text = buttonText,
            fontSize = 24.sp
        )
        Spacer(Modifier.size(12.dp))
        if (!ordered.value) {
            Icon(
                Icons.Rounded.ShoppingCart,
                contentDescription = "shopping cart"
            )
        }
    }
}

//Makes the profile screen
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun makeScreen() {
    //define host and scope for snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    //Snackbars require a scaffold to display on
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 24.dp)
//                .background(color = Color(0xFF8ED3D8))
        ) {
            Text(
                text = "Checkout",
                fontSize = 64.sp
            )
            Divider(
                Modifier.height(height = 12.dp),
                color = Color.Blue)
            //Make arrays to represent the various product attributes
            val itemCount = 4
            val images = arrayOf(
                R.drawable.watermelon,
                R.drawable.goggles,
                R.drawable.green_shirt,
                R.drawable.melon_ukulele)
            val names = arrayOf(
                "Watermelon, large",
                "Goggles, gray, size adult",
                "T-shirt, green, adult large",
                "Melon Ukulele")
            val quants = arrayOf(
                4,
                1,
                2,
                1
            )
            val prices = arrayOf(
                7.99,
                3.99,
                12.99,
                39.99)
            //build total price over loop
            var total = 0.0
            for (i in 0..3){
                total += quants[i] * prices[i]
                cartItem(images[i], names[i], quants[i], prices[i])
            }
            Spacer(Modifier.size(12.dp))
            //Make the summary total
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Total:",
                    fontSize = 36.sp
                )
                Text(
                    text = "$" + String.format("%.2f", total),
                    fontSize = 36.sp
                )
            }
            Spacer(Modifier.size(12.dp))
            checkoutButton(scope, snackbarHostState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    IndividualAssignment_25Theme {
        Card(modifier = Modifier.size(width = 412.dp, height = 915.dp)){
            makeScreen()
        }
    }
}