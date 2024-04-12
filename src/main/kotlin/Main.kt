import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun TopAppBarButton(
    text: String,
    imageVector: ImageVector,
    modifier: Modifier,
    buttonDescription: String = "Button",
    onClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                onClick()
            }) {
        Icon(
            imageVector = imageVector,
            contentDescription = buttonDescription
        )
        Text(text)
    }
}

@Composable
fun AboutAppScreen(
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(16.dp)
    ) {
        Text("About application")
    }
}

@Composable
fun InvoicesScreen(
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(16.dp)
    ) {
        Text("Invoices content")
    }
}

enum class Screen(name: String) {
    Invoices("Invoices"),
    AboutApp("About app")
}

@Composable
@Preview
fun App() {
    val invoices: String = Screen.Invoices.name
    val aboutApp: String = Screen.AboutApp.name

    var selectedTabString by remember { mutableStateOf(invoices) }
    val appColor = Color.Cyan

    MaterialTheme {
        Column {
            val fullScreenModifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .fillMaxWidth()

            TopAppBar(modifier = Modifier
                .height(40.dp), title = { }, actions = {
                TopAppBarButton(invoices, Icons.Default.List, modifier = fullScreenModifier, onClick = {
                    selectedTabString = invoices
                })
                TopAppBarButton(aboutApp, Icons.Default.Add, modifier = fullScreenModifier, onClick = {
                    selectedTabString = aboutApp
                })
            }, backgroundColor = appColor
            )

            if (selectedTabString == invoices)
                InvoicesScreen(modifier = fullScreenModifier)
            else
                AboutAppScreen(modifier = fullScreenModifier)

            //Spacer(modifier = Modifier.weight(1f))

            BottomAppBar(
                backgroundColor = appColor,
                modifier = Modifier
                    .height(25.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = "You are viewing " + selectedTabString,
                        modifier = Modifier
                            .align(Alignment.Center)
                        //.padding(16.dp)
                    )
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
