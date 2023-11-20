package com.example.sketchyrecall.helpers
import kotlin.random.Random

val fantasyWords = arrayOf("Monster", "Dragon", "Dinosaur", "Robot", "Creature", "Elf", "Goblin", "Werewolf", "Mermaid", "Witch" , "Gnome")
val animalWords = arrayOf(
    "Lion", "Elephant", "Giraffe", "Zebra", "Tiger",
    "Kangaroo", "Penguin", "Panda", "Koala", "Gorilla",
    "Dolphin", "Hippopotamus", "Cheetah", "Koala", "Ostrich",
    "Sloth", "Polar Bear", "Crocodile", "Peacock", "Meerkat"
)
val partWords = arrayOf("teeth", "wings", "beak", "legs", "arms", "claws", "feathers", "scales", "eyes", "fur", "ears", "talons", "glasses", "hat", "scarf", "boots", "shell", "nose", "tusks" )
val adjWords = arrayOf(
    "Tall", "Short", "Slim", "Chubby", "Elegant",
    "Graceful", "Energetic", "Lively", "Brilliant", "Witty",
    "Spirited", "Gentle", "Clever", "Vibrant", "Radiant",
    "Majestic", "Sleek", "Cozy", "Adventurous", "Sincere"
)
fun getRandomWord(strings:Array<String>):String{
        val randomIndex = Random.nextInt(strings.size)
        return strings[randomIndex]
    }
fun generatePrompt():String{
        val fantasy = getRandomWord(fantasyWords)
        val animal = getRandomWord(animalWords)
        val part = getRandomWord(partWords)
        val adj = getRandomWord(adjWords)
        return "coloring%20book%20page%20colorful%20colored%20${fantasy}%20${animal}%20${part}%20${adj}"
    }