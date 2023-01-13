package com.particeep.test.basic

/**
 * What is the complexity of the function ?

 * Refactor it to be a better function
 */
object Refactoring {

  case class File(
                   name:     String,
                   category: String
                 )

  /**
   *  RESPONSE: As it is written originally the time complexity of this function is O(n ^ 2)
   * This is because for each file we are going through the entire 'categories' list (in worst case) every time we are using
   * the 'contains' method that is O(N)
   * Also the append operation on a list is not the most efficient
   *
   * Anyway replacing the List by a Set, which allows to check the presence of an item in O(1),
   * allows us to reduce de time complexity to O(N)
   *
   * The space complexity remain unchanged and should be O(N) (in the worst case O(2N)
   * */
  def getCategories(files: List[File]): List[String] = {
    Option(files)
      .toList
      .flatten
      .foldLeft(Set.empty[String]) { (acc, e) =>
        if(e.category != null && !acc.contains(e.category))
          acc.+(e.category)
        else acc
      }.toList
  }

}
