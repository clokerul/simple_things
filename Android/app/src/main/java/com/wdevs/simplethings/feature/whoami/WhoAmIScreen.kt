package com.wdevs.simplethings.feature.whoami

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import com.ramcosta.composedestinations.annotation.Destination
import com.wdevs.simplethings.R

@Destination
@Composable
fun WhoAmIScreen() {
    val context = LocalContext.current
    val bluetoothAdapter: BluetoothAdapter =
        getSystemService(context, BluetoothManager::class.java)!!.adapter
    var isBtEnabled by remember {
        mutableStateOf(bluetoothAdapter.isEnabled)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = stringResource(R.string.whoamitext),
                textAlign = TextAlign.Justify,
                style = TextStyle(
                    fontSize = 26.sp,
                    shadow = Shadow(
                        color = Color.LightGray,
                        offset = Offset(5.0f, -10.0f),
                        blurRadius = 3f,
                    )
                )
            )
            Spacer(modifier = Modifier.size(100.dp))
            Row() {
                Button(
                    onClick = {
                        val bluetoothIntent: Intent
                        if (bluetoothAdapter?.isEnabled == false) {
                            bluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                            startActivityForResult(context as Activity, bluetoothIntent, 1, null)
                            isBtEnabled = true
                        } else {
                            if (ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.BLUETOOTH_CONNECT
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                            }
                            bluetoothAdapter?.disable()
                            isBtEnabled = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = if (isBtEnabled) Color.Green else Color.Red)
                ) {
                    Text(text = "Toggle Bluetooth")
                }
                Button(onClick = { }) {
                    Text(text = "Export list")
                }
            }
        }
    }
}

@Composable
@Preview
fun WhoAmIScreenPreview() {
    WhoAmIScreen()
}