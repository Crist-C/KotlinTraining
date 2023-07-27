/**
 *
 * Lamdas:
 *
 *  En Kotlin, las funciones se consideran construcciones de primera clase.
 *  Esto significa que las funciones se pueden tratar como un tipo de datos.
 *  Puedes almacenar funciones en variables, pasarlas a otras funciones
 *  como argumentos y mostrarlas desde otras funciones.
 *
 * Qué aprenderás
 *   Cómo definir una función con sintaxis lambda
 *   Cómo almacenar funciones en variables
 *   Cómo pasar funciones como argumentos a otras funciones
 *   Cómo mostrar funciones de otras funciones
 *   Cómo usar tipos de funciones anulables
 *   Cómo hacer que las expresiones lambda sean más concisas
 *   Qué es una función de orden superior
 *   Cómo usar la función repeat()
 */



/**
 * Resumen
 *   Las funciones en Kotlin son construcciones de primer nivel y se pueden tratar como tipos de datos.
 *   Las expresiones lambda proporcionan una sintaxis abreviada para escribir funciones.
 *   Puedes pasar tipos de funciones a otras funciones.
 *   Puedes mostrar un tipo de función desde otra.
 *   Una expresión lambda muestra el valor de la última expresión.
 *   Si se omite una etiqueta de parámetro en una expresión lambda con un solo parámetro, se hace referencia a ella con el identificador it.
 *   Las expresiones lambda se pueden escribir intercaladas sin un nombre de variable.
 *   Si el último parámetro de una función es un tipo de función, puedes usar la sintaxis lambda al final para mover la expresión lambda después del último paréntesis cuando llamas a una función.
 *   Las funciones de orden superior son funciones que toman otras funciones como parámetros o muestran una función.
 *   La función repeat() es una función de orden superior que funciona de manera similar a un bucle for.
 */

fun main() {

    val storeLambdaFunction = ::anyFunction
    //storeLambdaFunction()

    val havingOtherLambda = otherTrickFunction
    //havingOtherLambda()

    val coins: (Int) -> String = {
        nombreQueLeDeseoDarAlParametro -> "$nombreQueLeDeseoDarAlParametro coins"
    }

    val coinsShortened: (Int) -> String = {
        "$it coins"
    }

    val cupcakes: (Int) -> String = {
        "Have a cupcake!"
    }

    val treatFunction = trickOrTreat(false, coinsShortened)
    val trickFunction = trickOrTreat(true, null)

    treatFunction()
    trickFunction()


    val otherTreatFunction = trickOrTreat(false) { "$it coins" }
    otherTreatFunction()

    repeat(10) {
        println("Esta es la iteración #$it")
        trickOrTreat(false) { "$it coins" }()
    }
}

fun trickOrTreat(isTrick: Boolean, extraTreat: ((Int) -> String)? ) : () -> Unit {
    if (isTrick) {
        return trick
    }else {
        if (extraTreat != null) {
            println(extraTreat(5))
        }
        return treat
    }
}

// una variable que almacena una lamnda,
// que no recibe ningún parámetro "()"
// Y no retorna nada "Unit".
val treat : () -> Unit = {
    println("Have a treat!")
}

// al ser por defecto se pueden omitir () -> Unit
val otherTrickFunction = {
    println("Other trick!")
}

val trick = {
    println("No treats!")
}

fun anyFunction() {
    println("This a function")
}