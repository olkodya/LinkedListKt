
import java.io.IOException
import java.util.*


const val FRACTION_FILE_NAME = "fraction.out"
const val INTEGER_FILE_NAME = "integer.out"
fun printMenu() {
    println("Choose option:")
    println("[1] Add value")
    println("[2] Add value by index")
    println("[3] Get value by index")
    println("[4] Remove value by index")
    println("[5] Get size of list")
    println("[6] Sort list")
    println("[7] Print list")
    println("[8] Clear list")
    println("[9] Serialize list to Binary File")
    println("[10] Deserialize list from Binary File")
    println("[11] Exit")
}

fun menu() {
    var list = MyList()
    println("Choose data type: 1 - Integer, 2 - Fraction")
    val scanner = Scanner(System.`in`)
    val dataType: Int = scanner.nextInt()
    if (dataType == 1) {
        for (i in 0..9) {
            list.add(MyInteger.create())
        }
    } else {
        for (i in 0..9) {
            list.add(Fraction.create())
        }
    }
    var myInteger = MyInteger()
    var fraction: Fraction = Fraction()
    var index: Int
    if (scanner.hasNextLine()) scanner.nextLine()
    while (true) {
        printMenu()
        val option: Int = scanner.nextInt()
        when (option) {
            1 -> try {
                if (scanner.hasNextLine()) scanner.nextLine()
                if (dataType == 1) {
                    println("Enter integer value")
                    val value: String = scanner.nextLine()
                    myInteger = myInteger.parseValue(value) as MyInteger
                    list.add(myInteger)
                } else {
                    println("Enter fraction")
                    val value: String = scanner.nextLine()
                    fraction = fraction.parseValue(value) as Fraction
                    list.add(fraction)
                }
            } catch (ex: IllegalArgumentException) {
                println(ex.message)
            }

            2 -> if (list.getSize() == 0) println("List is empty, add something first") else {
                println("Enter index from 0 to " + list.getSize())
                index = scanner.nextInt()
                if (scanner.hasNextLine()) scanner.nextLine()
                if (dataType == 1) {
                    println("Enter integer value")
                    val value: String = scanner.nextLine()
                    try {
                    myInteger = myInteger.parseValue(value) as MyInteger
                        list.add(myInteger, index)
                    } catch (ex: IllegalArgumentException) {
                        println(ex.message)
                    }
                } else {
                    println("Enter fraction")
                    val value: String = scanner.nextLine()
                    try {
                    fraction = fraction.parseValue(value) as Fraction
                    list.add(fraction, index)
                    } catch (ex: IllegalArgumentException) {
                        println(ex.message)
                    }
                }
            }

            3 -> if (list.getSize() == 0) println("List is empty, there is no element to get") else {
                println("Enter index from 0 to " + (list.getSize() - 1))
                index = scanner.nextInt()
                if (scanner.hasNextLine()) scanner.nextLine()
                try {
                    println(list[index]!!.getData())
                } catch (ex: IllegalArgumentException) {
                    println(ex.message)
                }
            }

            4 -> if (list.getSize() == 0) println("List is empty, there is no element to remove") else {
                println("Enter index from 0 to " + (list.getSize() - 1))
                index = scanner.nextInt()
                if (scanner.hasNextLine()) scanner.nextLine()
                try {
                    println("Removed element:" + list.remove(index))
                } catch (ex: IllegalArgumentException) {
                    println(ex.message)
                }
            }

            5 -> println("Size of list:" + list.getSize())
            6 -> if (list.getSize() == 0) println("List is empty, add something first") else {
                list.printList()
                if (dataType == 1) {
                    list.quickSort(0, list.getSize() - 1, myInteger.typeComparator)
                } else {
                    list.quickSort(0, list.getSize() - 1, fraction.typeComparator)
                }
                list.printList()
            }

            7 -> list.printList()

            8-> if (list.getSize() == 0) println("List is empty, nothing to clear") else {
                list.clear()
                println("Successfully cleared")
            }
             9-> try {
                if (list.getSize() == 0) println("List is empty, add something first") else {
                    if(dataType == 1){
                        MyList.serializeToBinary(list, INTEGER_FILE_NAME)
                    }else {
                        MyList.serializeToBinary(list, FRACTION_FILE_NAME)
                    }
                }
            } catch (ex: IOException) {
                println(ex.message)
            }

            10 -> try {
                list = if(dataType == 1){
                    MyList.deserializeFromBinary(INTEGER_FILE_NAME)
                }else {
                    MyList.deserializeFromBinary(FRACTION_FILE_NAME)
                }
            } catch (ex: ClassNotFoundException) {
                println(ex.message)
            } catch (ex: IOException) {
                println(ex.message)
            }

            11 -> return
            else -> {}
        }
    }
}


fun main(args: Array<String>) {
    menu()
}