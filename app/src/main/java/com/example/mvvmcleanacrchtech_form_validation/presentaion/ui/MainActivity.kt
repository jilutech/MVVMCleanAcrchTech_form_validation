package com.example.mvvmcleanacrchtech_form_validation.presentaion.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvvmcleanacrchtech_form_validation.presentaion.RegistrationFormEvent
import com.example.mvvmcleanacrchtech_form_validation.presentaion.theme.MVVMCleanAcrchTech_form_validationTheme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMCleanAcrchTech_form_validationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = viewModel<MainViewModel>()
                    val state = viewModel.state
                    val context = LocalContext.current
                    LaunchedEffect(key1 = context){
                        viewModel.validationEvents.collect{ event ->

                            when(event){
                                is  MainViewModel.ValidationEvent.Success ->{
                                    Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
                                }

                            }

                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp), verticalArrangement = Arrangement.Center
                    ) {

                        TextField(
                            value = state.email,
                            onValueChange = {
                            viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
                            },
                            isError = state.emailError != null,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Email")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            )
                         )
                        if (state.emailError != null){
                            Text(text = state.emailError, color = MaterialTheme.colorScheme.error)
                        }

                        Spacer(modifier = Modifier.height(19.dp))


                        TextField(
                            value = state.password,
                            onValueChange = {
                                viewModel.onEvent(RegistrationFormEvent.PasswordChange(it))
                            },
                            isError = state.passwordError != null,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Password")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation()
                        )
                        if (state.passwordError != null){
                            Text(text = state.passwordError, color = MaterialTheme.colorScheme.error)
                        }

                        Spacer(modifier = Modifier.height(19.dp))



                        TextField(
                            value = state.repeatedPassword,
                            onValueChange = {
                                viewModel.onEvent(RegistrationFormEvent.PasswordRepeatedError(it))
                            },
                            isError = state.repeatedPasswordError != null,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Repeated Password")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation()
                        )
                        if (state.repeatedPasswordError != null){
                            Text(text = state.repeatedPasswordError, color = MaterialTheme.colorScheme.error)
                        }

                        Spacer(modifier = Modifier.height(19.dp))


                        Row(modifier = Modifier.fillMaxWidth()) {
                            
                            Checkbox(checked = state.acceptedTerms, onCheckedChange = {
                                viewModel.onEvent(RegistrationFormEvent.AcceptTerms(it))
                            })
                            Spacer(modifier = Modifier.height(19.dp))

                            Text(text = "Accept Terms")
                            if (state.termsError != null){
                                Text(text = state.termsError, color = MaterialTheme.colorScheme.error)
                            }
                            Button(
                                onClick = {
                                    viewModel.onEvent(RegistrationFormEvent.Submit)
                                },                                  )
                            {
                                Text(text = "Submit")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MVVMCleanAcrchTech_form_validationTheme {
        Greeting("Android")
    }
}