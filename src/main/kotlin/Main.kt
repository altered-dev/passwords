import java.io.File
import kotlin.random.Random

val passwords = File("src/main/resources/passwords.txt")
    .readLines()
    .zip(
        File("src/main/resources/users.txt")
            .readLines()
            .map(String::toInt)
    )

fun getRandomPassword() = passwords[Random.nextInt(passwords.size)]

tailrec fun main() {
    println("""
        Добро пожаловать в Парольгадайку!!! Задача игры - угадать, какой пароль используется чаще.
        Будут даны два пароля, у первого показано число пользователей, у второго - нет.
        Если вы думаете, что у второго пароля больше пользователей, чем у первого, пишите +, иначе -.
        Чтобы выйти, напишите exit / выход.
        Удачи!
        
    """.trimIndent())

    var previous = getRandomPassword()

    while (true) {
        var current = getRandomPassword()
        while (current == previous) current = getRandomPassword()

        println("""
            Паролем '${previous.first}' пользуются ${previous.second} человек!
            Как вы думаете, у пароля '${current.first}' больше или меньше пользователей?
        """.trimIndent())

        var input = readln()

        while (input !in listOf("exit", "выход", "+", "-")) {
            println("Не понимаю, что тут написано...")
            input = readln()
        }

        if (input in listOf("exit", "выход")) {
            println("До связи.")
            return
        }

        if ((current.second > previous.second) != (input == "-")) {
            println("Правильно! Продолжаем!")
            previous = current
        } else {
            println("""
                Вы проиграли :( 
                Паролем '${current.first}' на самом деле пользуются ${current.second} человек! 
                Начнём снова!
                
                """.trimIndent())
            main()
            return
        }
    }
}