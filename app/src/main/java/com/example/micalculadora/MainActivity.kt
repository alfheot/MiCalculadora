package com.example.micalculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import com.example.micalculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    // PASO 1 - FIJO -----------------------> // variable que creamos para operar el binding
    private lateinit var binding: ActivityMainBinding
    private var contador = 0
    private var numeroDeTecla: Int? = null
    var numeroEnPantalla = ""
    var resultado: Int = 0
    var operacion: Int? = null
    var op1: Int = 0
    var op2: Int = 0
    var operando: Boolean = false
    lateinit var teclaPulsada: CharSequence
    lateinit var ultimoCaracterTeclaPulsada: String
    var textoOperacion = "";
    var texto = "";
    val integerChar = '0'..'9'
    var textoSuperior = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


                //setContentView(R.layout.activity_main) //quitamos la vista por defecto
                //botonNumero1 = findViewById(R.id.botonNumero1) // así se haría uno a uno

        // PASO 2 - FIJO -----------------------> Llenamos la variable con el contenido del layout
        binding = ActivityMainBinding.inflate(layoutInflater)


        
        // PASO 3 - FIJO ----------------------->Ponemos la vista que contiene la variable "binding"
        setContentView(binding.root)

        //Insertamos el estado que guardamos
        resultado = savedInstanceState?.getInt("resultado") ?: 0
        binding.pantallaPrincipal.text = resultado.toString()

        textoSuperior = savedInstanceState?.getString("textoSuperior") ?: ""
        binding.textoOperacion?.text = textoSuperior

        binding.botonResetC.setOnClickListener(this)
        binding.botonCambioSignoG.setOnClickListener (this)
        binding.botonPorcentajeP.setOnClickListener(this)
        binding.botonDivisionB.setOnClickListener(this)
        binding.botonMultiplicacionX.setOnClickListener(this)
        binding.botonRestaR.setOnClickListener(this)
        binding.botonSumaM.setOnClickListener(this)
        binding.botonIgualI.setOnClickListener(this)
        binding.botonComa.setOnClickListener(this)
        binding.botonNumero0.setOnClickListener(this)
        binding.botonNumero1.setOnClickListener(this)
        binding.botonNumero2.setOnClickListener(this)
        binding.botonNumero3.setOnClickListener(this)
        binding.botonNumero4.setOnClickListener(this)
        binding.botonNumero5.setOnClickListener(this)
        binding.botonNumero6.setOnClickListener(this)
        binding.botonNumero7.setOnClickListener(this)
        binding.botonNumero8.setOnClickListener(this)
        binding.botonNumero9.setOnClickListener(this)
    }

    private fun imprimirHistorico(c: String): String {
        println("----->"+ c)
        if(c.all { it.isDigit() }) {
            texto += c;
        }else{
            when(c){
                "B"-> texto += " / "
                "X"-> texto += " X "
                "R"-> texto += " - "
                "M"-> texto += " + "
                "P"-> texto += " % "
                "G"-> texto += " +/- "
                "I"-> texto += " = "
                "C"-> texto = ""
            }
        }
        return texto;
    }

    override fun onClick(v: View){

        // Obtengo el nombre del ID que le he puesto en el diseño
        var teclaPulsada = resources.getResourceEntryName(v.id)
        println("teclaPulsada: "+ teclaPulsada)
        // Obtengo el último carácter de ese ID
        var ultimoCaracterTeclaPulsada = teclaPulsada.last().toString()

        //binding.textoOperacion?.text = numeroEnPantalla + ultimoCaracterTeclaPulsada;
        textoSuperior = imprimirHistorico(ultimoCaracterTeclaPulsada)
        binding.textoOperacion?.text = textoSuperior

        //val integerChar = '0'..'9'
        fun comprobarSiEsNumEntre0y9(str: String): Boolean {
            val regex = Regex("[0-9]")
            return regex.matches(str)
        }

        if(operando){ //cuando procede el click justo después del click de una operación hay que limpiar la pantalla
            println("lllllllllllllllllllll")
            binding.pantallaPrincipal.text = "" //reseteo la pantalla antes de volver a escribir
            if(comprobarSiEsNumEntre0y9(ultimoCaracterTeclaPulsada)){
                binding.pantallaPrincipal.text = ultimoCaracterTeclaPulsada // escribo el primer nº del segundo operando comprobando antes si lo que se ha puslsado es un número

            }else{
                println("-l-l-l-ll-l-l-l-l-l-l-l-l-l-l-l-l-ll-")
            }

        }
        numeroEnPantalla = binding.pantallaPrincipal.text.toString() // con toString() el contenido en String pq parece que lo que hay es un objeto de tipo CharSequence

        println("Operando A: " + operando)


        //val tipoDeDato = numeroEnPantalla::class.simpleName
        //println("El tipo de dato de numeroEnPantalla es: $tipoDeDato")

        if(ultimoCaracterTeclaPulsada in ("0".."9")){

            //Quito el 0 cuando está él solo.
            if(numeroEnPantalla.equals("0")) numeroEnPantalla = "";

            if(operando){
                introducirNumerosEnPantalla("", ultimoCaracterTeclaPulsada)
                operando = false
            }else {
                introducirNumerosEnPantalla(numeroEnPantalla, ultimoCaracterTeclaPulsada)
            }

        }
        else{
            when(teclaPulsada){
                "botonResetC" -> {
                    binding.pantallaPrincipal.text = "0"
                    op1 = 0
                    op2 = 0
                }
                "botonCambioSignoG" -> {
                    binding.pantallaPrincipal.text = (numeroEnPantalla.toString().toInt() * (-1)).toString()
                }
                "botonPorcentajeP"-> {
                    binding.pantallaPrincipal.text = (numeroEnPantalla.toString().toInt() / (100)).toString()
                    println("%")

                }
                "botonSumaM" ->{
                    println("Operando (a la entrada de Sumar): " + operando )
                    /*println("------------------------------")
                      println("Al entrar:")
                      imprimeValores()
                      println("------------------------------")*/
                    operacion = 0
                    if(operando){
                        println("cazurro1")
                        op1 = numeroEnPantalla.toInt()       // Guardo el número que hay en pantalla en op1
                        binding.pantallaPrincipal.text = op1.toString()

                    }else{
                        op1 = op1 + numeroEnPantalla.toInt() // en lugar de guardar el nuevo número tecleado en op2 lo sumo a op1
                                                             // y op2 sólo lo usaré donde se define el signo de igual
                                                             // "resultado" sólo lo usaré donde se define el signo de igual
                        binding.pantallaPrincipal.text = op1.toString()
                        operando = true
                    }

                    //resultado = resultado?.toInt() ?: 0 + op1!!.toInt()

                    //SEGUIR A PARTIR DE AQUÍ´: SIGUIENTE LINEA FALLA ------------------------------------------------
                    //binding.pantallaPrincipal.text = resultado.toString() // reseteo Número en Pantalla (pq si lo hiciera igualando numeroEnPantalla = 0, estaría quitando que fuera lo que puse originalmente ->  var numeroEnPantalla = binding.pantallaPrincipal.text.toString()


                    //numeroEnPantalla =  binding.pantallaPrincipal.text.toString()

                    println("------------------------------")
                    println("Al salir:")
                    imprimeValores()
                    println("------------------------------")


                }
                "botonRestaR" ->{
                    /*println("------------------------------")
                      println("Al entrar:")
                      imprimeValores()
                      println("------------------------------")*/

                   // op1 = numeroEnPantalla.toInt() // Guardo el número que hay en pantalla en op1

                    operacion = 1
                    if(operando){
                        println("blablabla")
                        op2 = numeroEnPantalla.toInt()  // como viene "operando" hay ya un op1, por tanto guardo aquí el op2
                        resultado = op1 - op2
                        binding.pantallaPrincipal.text = resultado.toString()

                    }else{
                        println("kkkk")
                        op1 = numeroEnPantalla.toInt() // en lugar de guardar el nuevo número tecleado en op2 lo sumo a op1
                                                       // y op2 sólo lo usaré donde se define el signo de igual
                                                       // "resultado" sólo lo usaré donde se define el signo de igual
                        //binding.pantallaPrincipal.text = op1.toString()
                        operando = true
                    }

                    println("------------------------------")
                    println("Al salir:")
                    imprimeValores()
                    println("------------------------------")

                }
                "botonMultiplicacionX" ->{
                    /*println("------------------------------")
                      println("Al entrar:")
                      imprimeValores()
                      println("------------------------------")*/
                    operacion = 2

                    if(operando){
                        println("cazurro3")
                        op2 = numeroEnPantalla.toInt() // como viene "operando" hay ya un op1, por tanto guardo aquí el op2
                        resultado = op1 * op2
                        binding.pantallaPrincipal.text = resultado.toString()
                    }
                    else{
                        op1 = numeroEnPantalla.toInt() // Guardo el número que hay en pantalla en op1
                        operando = true
                    }

                    println("------------------------------")
                    println("Al salir:")
                    imprimeValores()
                    println("------------------------------")

                }
                "botonDivisionB" ->{
                    /*println("------------------------------")
                      println("Al entrar:")
                      imprimeValores()
                      println("------------------------------")*/
                    if(operando){
                        println("cazurro3")
                        op2 = numeroEnPantalla.toInt() // como viene "operando" hay ya un op1, por tanto guardo aquí el op2
                        resultado = op1 / op2
                        binding.pantallaPrincipal.text = resultado.toString()
                    }
                    else{
                        op1 = numeroEnPantalla.toInt() // Guardo el número que hay en pantalla en op1
                        operando = true
                    }
                    operacion = 3
                    operando = true

                    println("------------------------------")
                    println("Al salir:")
                    imprimeValores()
                    println("------------------------------")

                }
                "botonIgualI" ->{

                    op2 = numeroEnPantalla.toInt()
                    when(operacion){
                        0->{
                            resultado = op1 + op2
                            op1 = 0
                            op2 = 0
                            binding.pantallaPrincipal.text = resultado.toString()
                            numeroEnPantalla = resultado.toString()
                        }
                        1->{
                            resultado = op1 - op2
                            op1 = 0
                            op2 = 0
                            binding.pantallaPrincipal.text = resultado.toString()
                            numeroEnPantalla = resultado.toString()
                        }
                        2->{
                            resultado = op1 * op2
                            op1 = 0
                            op2 = 0
                            binding.pantallaPrincipal.text = resultado.toString()
                            numeroEnPantalla = resultado.toString()
                        }
                        3->{
                            resultado = op1 / op2
                            op1 = 0
                            op2 = 0
                            binding.pantallaPrincipal.text = resultado.toString()
                            numeroEnPantalla = resultado.toString()
                        }
                    }
                    println("------------------------------")
                    imprimeValores() // solo por ver lo resultados en consola
                    println("------------------------------")


                    binding.pantallaPrincipal.text = resultado.toString()

                }
                "botonComa" ->{
                    println(",")
                }
            }

        }





        when(v?.id){
            binding.botonResetC.id ->{
                binding.pantallaPrincipal.text = "0"
            }
        }
    }

    fun introducirNumerosEnPantalla( numeroEnPantalla: String, numeroPulsado: String ): String{
            binding.pantallaPrincipal.text = numeroEnPantalla + numeroPulsado
            var nuevoNumeroEnPantalla = binding.pantallaPrincipal.text.toString() // por lo visto tengo que reasignar el valor a otra variable pq no puedo sobrescribir el de la declarada como parámetro
            println("numeroEnPantalla--->"+ nuevoNumeroEnPantalla)

            // cada vez que se mete un número, la función devuelve el número completo que hay en pantalla
            return nuevoNumeroEnPantalla;
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("resultado", resultado)
        outState.putString("textoSuperior", textoSuperior)
    }

    fun imprimeValores(){

        println("Operador 1: " + op1 + " Tipo: " + op1::class.simpleName)
        println("Operador 2: " + op2  + " Tipo: " + op2::class.simpleName)
        println("Resultado: " + resultado  )
        println("Número en Pantalla: " + numeroEnPantalla + " Tipo: " + numeroEnPantalla::class.simpleName)
        println("operando: " + operando )
        //println("Tecla pulsada: " + teclaPulsada)
        //println("Ultimo carácter pulsado: " + ultimoCaracterTeclaPulsada)
    }








}

