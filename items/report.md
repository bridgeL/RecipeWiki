# [G0 - Team Name] Report

The following is a report template to help your team successfully provide all the details necessary for your report in a structured and organised manner. Please give a straightforward and concise report that best demonstrates your project. Note that a good report will give a better impression of your project to the reviewers.

Note that you should have removed ALL TEMPLATE/INSTRUCTION textes in your submission (like the current sentence), otherwise it hampers the professionality in your documentation.

*Here are some tips to write a good report:*

* `Bullet points` are allowed and strongly encouraged for this report. Try to summarise and list the highlights of your project (rather than give long paragraphs).*

* *Try to create `diagrams` for parts that could greatly benefit from it.*

* *Try to make your report `well structured`, which is easier for the reviewers to capture the necessary information.*

*We give instructions enclosed in square brackets [...] and examples for each sections to demonstrate what are expected for your project report. Note that they only provide part of the skeleton and your description should be more content-rich. Quick references about markdown by [CommonMark](https://commonmark.org/help/)*

## Table of Contents

- [\[G0 - Team Name\] Report](#g0---team-name-report)
  - [Table of Contents](#table-of-contents)
  - [Administrative](#administrative)
  - [Team Members and Roles](#team-members-and-roles)
  - [Summary of Individual Contributions](#summary-of-individual-contributions)
  - [Application Description](#application-description)
    - [Application Use Cases and or Examples](#application-use-cases-and-or-examples)
      - [Target users](#target-users)
      - [Use cases](#use-cases)
    - [Application UML](#application-uml)
  - [Code Design and Decisions](#code-design-and-decisions)
    - [Data Structures](#data-structures)
    - [Design Patterns](#design-patterns)
    - [Parser](#parser)
    - [Grammar(s)](#grammars)
    - [Tokenizers and Parsers](#tokenizers-and-parsers)
    - [Others](#others)
  - [Implemented Features](#implemented-features)
    - [Basic Features](#basic-features)
    - [Custom Features](#custom-features)
    - [Surprise Features](#surprise-features)
  - [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
  - [Testing Summary](#testing-summary)
  - [Team Management](#team-management)
    - [Meetings Records](#meetings-records)
    - [Conflict Resolution Protocol](#conflict-resolution-protocol)

## Administrative
- Firebase Repository Link: <insert-link-to-firebase-repository>
   - Confirm: I have already added comp21006442@gmail.com as a Developer to the Firebase project prior to due date.
- Two user accounts for markers' access are usable on the app's APK (do not change the username and password unless there are exceptional circumstances. Note that they are not real e-mail addresses in use):
   - Username: comp2100@anu.edu.au	Password: comp2100
   - Username: comp6442@anu.edu.au	Password: comp6442

## Team Members and Roles
The key area(s) of responsibilities for each member

| UID      |      Name      |                                                                                                                                                                                                                                      Role |
| :------- | :------------: | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |
| u7760022 |   Xinyang Li   |                                                                                                                             Backend (Login, DataFiles, LoadShowData, DataStream, Search-Invalid, FB-Auth, FB-Persist-extension, Register) |
| u7752874 |   Xinlei Wen   |                                                                                    Backend (LoadShowData, Search, Data-Formats)                                                                                                    [role] |
| u7754676 | Tashia Tamara  |                                                                                                                                                                                                  Backend (Login, Search-Filter, Data-GPS) |
| u7759982 | Jiangbei Zhang |                                             Frontend (Login, Data-Profile, Register)                                                                                                                                               [role] |
| u7693070 |  Changlai Sun  | Frontend (LoadShowData, Search, Search-Filter)                                                                                                                                                                                     [role] |


## Summary of Individual Contributions

1. **U7760022, Xinyang Li**  I have 20% contribution, as follows: 
- **Code Contribution in the final App**
    - Feature Feat 1 login, Feat 2 dataFiles, Feat 3 LoadShowData, Feat 4 DataStream, Feat 6 search-invalid, Feat 11 fb-auth, Feat 12 fb-persist-extension
        - [UserSimulator.java](app/src/main/java/anu/cookcompass/datastream/UserSimulator.java)
        - [CloudData.java](app/src/main/java/anu/cookcompass/firebase/CloudData.java)
        - [Login.java](app/src/main/java/anu/cookcompass/login/Login.java)
        - [Register.java](app/src/main/java/anu/cookcompass/login/Register.java)
        - [PopMsgManager.java](app/src/main/java/anu/cookcompass/popmsg/PopMsgManager.java)
        - [RecipeManager.java](app/src/main/java/anu/cookcompass/recipe/RecipeManager.java)
        - [SearchService.java](app/src/main/java/anu/cookcompass/search/SearchService.java)
        - [UserManager.java](app/src/main/java/anu/cookcompass/user/UserManager.java)
    - Design Pattern 
        - Singleton & Factory
            - [SingletonFactory.java](app/src/main/java/anu/cookcompass/pattern/SingletonFactory.java)
        - Facade
            - [SearchService.java search()](app/src/main/java/anu/cookcompass/search/SearchService.java#L59)
        - Observer
            - interface: 
                - [Observer.java](app/src/main/java/anu/cookcompass/pattern/Observer.java)
                - [Subject.java](app/src/main/java/anu/cookcompass/pattern/Subject.java)
            - implementation:
                - [CloudData.java](app/src/main/java/anu/cookcompass/firebase/CloudData.java)
    - Data Structure
        - Binary Search Tree 
            - [BinarySearchTree.java](app/src/main/java/anu/cookcompass/model/BinarySearchTree.java)

- **Code and App Design** 
    - [What design patterns, data structures, did the involved member propose?]*
    - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* 

2. **U7759982, Jiangbei Zhang**  I have 20% contribution, as follows: 
- **Code Contribution in the final App**
    - Feature Feat 1 login, Feat 9 data Profile, Feat 4 data stream 
      - class ProfileFragment: [ProfileFragment.java](https://gitlab.cecs.anu.edu.au/u7760022/gp-24s1/app/src/main/java/anu/cookcompass/user/ProfileFragment.java)
      - class NotificationFragment:[NotificationFragment.java](app/src/main/java/anu/cookcompass/popmsg/NotificationFragment.java)
      - class NotificationAdapter:[NotificationAdapter.java](app/src/main/java/anu/cookcompass/popmsg/NotificationAdapter.java)
    - Other UI realated java class 
      - class MainActivity [MainActivity.java](app/src/main/java/anu/cookcompass/MainActivity.java)
      - class BottomBarActivity [BotommBarActivity.java](app/src/main/java/anu/cookcompass/BottomBarActivity.java)
      - class CircleImageView [CircleImageView.java](app/src/main/java/anu/cookcompass/theme/CircleImageView.java)
      - class RegisterActivity [RegisterActivity.java](app/src/main/java/anu/cookcompass/login/RegisterActivity.java)
      - class BottomBarActivity [BottomBarActivity.java](app/src/main/java/anu/cookcompass/BottomBarActivity.java)
    - 

- **Code and App Design**
    - UI Design
      - UI design for the login page: [activity_login.xml](app/src/main/res/layout/activity_login.xml)
      - UI design for the main page: [activity_main.xml](app/src/main/res/layout/activity_main.xml)
      - UI design for the nagivation bar:[activity_navigation_bar.xml](app/src/main/res/layout/activity_navigation_bar.xml)
      - UI design for the fragment_notification page:[fragment_notification.xml](app/src/main/res/layout/fragment_notification.xml)
      - UI design for the fragment_profile page:[fragment_profile.xml](app/src/main/res/layout/fragment_profile.xml)
      - UI design for the notification_item:[notification_item.xml](app/src/main/res/layout/notification_item.xml)
      - UI design for bottom_bar_background:[bottom_bar_bg.xml](app/src/main/res/menu/bottom_bar_bg.xml)
      - UI design for profile_text background:[profile_text_background.xml](app/src/main/res/drawable/profile_text_background.xml)
      - UI design for login account icon:[baseline_account_box_24.xml](app/src/main/res/drawable/baseline_account_box_24.xml)
      - UI design for register back arrow icon:[round_arrow_back_24.xml](app/src/main/res/drawable/round_arrow_back_24.xml)
    - 
    - External resources of my UI part
      - ![account_icon.png](..%2Fapp%2Fsrc%2Fmain%2Fres%2Fdrawable%2Faccount_icon.png) free for non-commercial use, CC BY "best_leeyang"
      - ![lock_image.png](..%2Fapp%2Fsrc%2Fmain%2Fres%2Fdrawable%2Flock_image.png) free for non-commercial use, CC BY "逍剑"
      - ![notification_icon.png](..%2Fapp%2Fsrc%2Fmain%2Fres%2Fdrawable%2Fnotification_icon.png) free for non-commercial use, CC BY "best_leeyang"
      - ![search_icon.png](..%2Fapp%2Fsrc%2Fmain%2Fres%2Fdrawable%2Fsearch_icon.png) free for non-commercial use, CC BY "best_leeyang"


## Application Description

CookCompass is a recipe application targeted towards people are interested in cooking. It provides thousands of recipes featuring lots of different ingredients. Users can sort the recipes based on their id number, title, likes, and views. They can also look for recipes of a certain level of popularity, for example recipes with at least 100 likes or views. 

![](media/Screenshots/1.jpg)

<details>
<summary>more screenshot</summary>

![](media/Screenshots/2.jpg)
![](media/Screenshots/3.jpg)
![](media/Screenshots/4.jpg)
![](media/Screenshots/5.jpg)
![](media/Screenshots/6.jpg)
</details>

### Application Use Cases and or Examples

#### Target users 

Home cooks / People interested in cooking

* Users can use it to look up various kinds of recipes.
* Users can use the app's search feature to look up recipes by typing in keywords into the search bar.
* Users can like recipes, thereby adding to the recipe's like count.
* Every time another user of the app likes or unlikes recipes, the user will receive a notification. The user can also see which location this other user is liking/unliking the recipe from. This helps the user keep track of in which countries certain recipes are currently gaining or losing popularity. 
* Users can sort the recipe search results according to the recipe's id number, title, likes, and views.
* Users can also customize their app experience by changing their profile picture and/or changing the app's theme color.

#### Use cases

- Looking Up Recipes:
    - Alice wants to find a new dessert recipe to try out this weekend. She opens the app and types "chocolate cake" into the search bar. The app displays a list of chocolate cake recipes sorted by popularity. Alice browses through the recipes, looking at the ratings and views to decide which one to try.

- Liking a Recipe:
    - Bob tries a new pasta recipe he found on the app and loves it. He decides to like the recipe, adding to its like count. Later, he receives a notification that someone in Italy has also liked the same recipe. Bob finds it interesting to see that the recipe is gaining popularity in different parts of the world.

- Receiving Notifications:
    - Catherine enjoys exploring different cuisines and likes recipes from various countries. She gets a notification that a user in Japan has liked a sushi recipe she recently discovered. She appreciates these notifications as they help her see global trends in cooking.

- Sorting and Filtering Recipes:
    - David is planning a dinner party and wants to impress his guests with a popular dish. He uses the app's search feature to look up "dinner recipes" and sorts the results by likes. He then filters the results to show the top 10 recipes with at least 100 likes. This helps him quickly find tried-and-tested recipes that are likely to be a hit at his party.

- Customizing the App Experience:
    - Emma loves personalizing her apps. She changes her profile picture to a photo of her favorite dish and updates the app's theme color to match her kitchen decor. This makes the app feel more tailored to her preferences and enhances her user experience.

<hr> 

### Application UML

![uml](media/UMLDiagrams/uml.png)

<details> 
<summary>UML Subdiagrams (Based on Package)</summary>

![cookcompass-uml-diagram](media/UMLDiagrams/cookcompass/cookcompass-uml-diagram.png)
![datastream-uml-diagram](media/UMLDiagrams/cookcompass/datastream/datastream-uml-diagram.png) 
![firebase-uml-diagram](media/UMLDiagrams/cookcompass/firebase/firebase-uml-diagram.png)
![gps-uml-diagram](media/UMLDiagrams/cookcompass/gps/gps-uml-diagram.png)
![login-uml-diagram](media/UMLDiagrams/cookcompass/login/login-uml-diagram.png)
![model-uml-diagram](media/UMLDiagrams/cookcompass/model/model-uml-diagram.png) 
![pattern-uml-diagram](media/UMLDiagrams/cookcompass/pattern/pattern-uml-diagram.png) 
![popmsg-uml-diagram](media/UMLDiagrams/cookcompass/popmsg/popmsg-uml-diagram.png) 
![recipe-uml-diagram](media/UMLDiagrams/cookcompass/recipe/recipe-uml-diagram.png) 
![search-uml-diagram](media/UMLDiagrams/cookcompass/search/search-uml-diagram.png)
![theme-uml-diagram](media/UMLDiagrams/cookcompass/theme/theme-uml-diagram.png) 
![user-uml-diagram](media/UMLDiagrams/cookcompass/user/user-uml-diagram.png)

</details>

<hr>

## Code Design and Decisions

This is an important section of your report and should include all technical decisions made. Well-written justifications will increase your marks for both the report as well as for the relevant parts (e.g., data structure). This includes, for example,

- Details about the parser (describe the formal grammar and language used)

- Decisions made (e.g., explain why you chose one or another data structure, why you used a specific data model, etc.)

- Details about the design patterns used (where in the code, justification of the choice, etc)

*Please give clear and concise descriptions for each subsections of this part. It would be better to list all the concrete items for each subsection and give no more than `5` concise, crucial reasons of your design.

<hr>

### Data Structures

*[What data structures did your team utilise? Where and why?]*

*I used the following data structures in my project:*

1. *BinearySearchTree*
   * *Objective: used for *
   * *Code Locations: defined in [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
   * *Reasons:*
      * *It is more efficient than Arraylist for insertion with a time complexity O(1)*
      * *We don't need to access the item by index for xxx feature because...*
      * For the (part), the data ... (characteristics) ...


<hr>

### Design Patterns
*[What design patterns did your team utilise? Where and why?]*

1. Singleton
    * Objective: used for implementation of Sing of a lot of classes
    * Code Locations: defined in [Class Singleton Factory](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
    * Reasons: avoid writing a lot of code to impletent singleton

2. SingletonFactory
    * Objective: used for implementation of Sing of a lot of classes
    * Code Locations: defined in [Class Singleton Factory](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
    * Reasons: avoid writing a lot of code to impletent singleton

3. Observer
    * Objective: used for implementation of Sing of a lot of classes
    * Code Locations: defined in [Class Singleton Factory](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
    * Reasons: avoid writing a lot of code to impletent singleton

4. Facade
    * Objective: used for implementation of Sing of a lot of classes
    * Code Locations: defined in [Class Singleton Factory](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
    * Reasons: avoid writing a lot of code to impletent singleton


<hr>

### Parser

### <u>Grammar(s)</u>
*[How do you design the grammar? What are the advantages of your designs?]*
*If there are several grammars, list them all under this section and what they relate to.*

Production Rules:

```#
    <Query> ::= <Ingr-Query> <Titl-Query> <Like-Query> <View-Query>
    <Ingr-Query> ::= ingredients = <Names>; | EMPTY
    <Titl-Query> ::= title = <Names>; | EMPTY
    <Like-Query> ::= like <BoolOperator> INTEGER ; | EMPTY
    <View-Query> ::= view <BoolOperator> INTEGER ; | EMPTY
    <Names> ::== STRING | STRING , <Names>
    <BoolOperator> ::=  > | <  | = 
```
Note that in this grammar, query to any data type (ingredients, title, etc.) could not appear more than once, and must follow the order given by the first equation.

This grammar is designed to support searching certain recipe with given title or ingredients, and do filtering on the like or view number. By applying this grammar, we could not only search keywords, but also filtering the result, which makes the filtering page clearer.

Specifically, in the actual application, a search invalid feature is implemented, allows user to search with an input that does not fully satisfies this grammar. However, this feature is not implemented in parser. For testing the parser, a fully correct input is still required.



### <u>Tokenizers and Parsers</u>

*[Where do you use tokenisers and parsers? How are they built? What are the advantages of the designs?]*

We implemented one tokenizer and one parser in our application, which is used as a supportive module of the searching functionality (see `Tokenizer.java` and `Parser.java` in package `search`). No external libraries that provides tokenizing or parsing are used, and the implementation has a simiilar format as the one provided in labs.

In a searching process, user enters their need formatted in the formal grammar shown above, and the tokenizer and parser will then be called, creating an object that contains every information in the input (object `QueryObject`). The search module only handles this query object.

By using a tokenizer and parser, it is easier to parse search inputs, enabling a clear filtering rule for the search module to perform its task. Besides, it separates the raw input from the frontend with the search interface in the backend, protects the search module from invalid inputs.

<hr>

### Others

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*


<hr>

## Implemented Features
*[What features have you implemented? where, how, and why?]* 
*List all features you have completed in their separate categories with their featureId. THe features must be one of the basic/custom features, or an approved feature from Voice Four Feature.*

### Basic Features
1. [LogIn]. Users must be able to log in (not necessarily sign up). (easy)
    * Code: [Login](https://gitlab.cecs.anu.edu.au/u7760022/gp-24s1/blob/f5a80187285f728941cf73ea7fdfb282a9a991f3/app/src/main/java/anu/cookcompass/login/Login java) and Class Y, ...
    * Description of feature:  
    * Description of your implementation: ... 

2. [DataFiles]. Create a dataset with at least 2,500 valid data instances, each representing a meaningful piece of information in your app. The data should be represented and stored in a structured format taught in the course. (easy)
    * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
    * Link to the Firebase repo: ...

3. [LoadShowData] Load and display data instances from your dataset. Choose an appropriate format to present the different types of data. (easy)
    * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
    * Link to the Firebase repo: ...

4. [DataStream] Create data instances to simulate users’ actions and interactions, which are then used to feed the app so that when a user is logged in, these data are loaded at regular time intervals and visualised on the app. (medium)
    * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
    * Link to the Firebase repo: ...

5. [Search] Users must be able to search for information on your app. (medium)
    * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
    * Link to the Firebase repo: ...

### Custom Features

Feature Category: Search-related features

6. [Search-Invalid]On top of giving search results from valid inputs, search functionality can process and correctly handle partially invalid search queries and give meaningful results. (medium)
    * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * Description of your implementation: ... 

7. [Search-Filter] Sort and filter a list of items returned from searches, with the use of suitable UI components. (easy)
    * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * Description of your implementation: ... 

Feature Category:Greater Data Usage, Handling and Sophistication 

8. [Data-Formats] Read data from local files in at least 2 different formats (JSON, XML, etc.). (easy)
    * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * Description of your implementation: ... 
      
9. [Data-Profile]  Create a Profile Page for Users or any Entities, which contains a media file (image, animation (e.g., gif), video). (easy) 
    * Code: [ProfileFragment.java](app/src/main/java/anu/cookcompass/user/ProfileFragment.java)
    * This is a fragment to show profile data from the firebase, and can synchronized with the firebase.
    * By clicking the profile button in the navigation bar, the application will jump to the profile fragment.
    * In the fragment, the users' email address, location, and profile image will be displayed. UserManger is 
    * used to get instance for current user, you can also upload the profile image by click the profile image. 
    * After clicking the image, the image picker will start which enables you to choose the image 
    * from phone local storage. At the same time, the image will be also be uploaded to the firebase.
    * Next time when you login this user, the image will be loaded automatically from the firebase.

10. [Data-GPS] Use GPS information based on location data in your App. (easy)
    * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * Description of your implementation: ... 

Feature Category:Firebase Integration

11. [FB-Auth] Use Firebase to implement User Authentication/Authorisation. (easy)
    * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * Description of your implementation: ... 

12. [FB-Persist-extension] Use Firebase to persist all data used in your app. (medium)
    * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * Description of your implementation: ... 

13. [FB-Register] Users are able to sign up, and the relevant user instance will be created in firebase
    * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * Description of your implementation: ... 

<hr>

### Surprise Features

- If implemented, explain how your solution addresses the task (any detail requirements will be released with the surprise feature specifications).
- State that "Suprised feature is not implemented" otherwise.

Four existing code smells:

1. frontend design structure

- description: At first, we try to implement all UI page by activity. However, in this way, it is very hard to expand the 
  - present code. Every activity need a intent to switch from one to another. The logic to switch among activities need to be 
  - take into serious consideration. Then, we refactored the UI structure by introducing navigation bar combined with fragments. 
  - In this way, it enables us to switch easily between our pages and privide a better guidance for the user. 
  - In addition, it makes us easier to add new pages.
- Previous gits: https://gitlab.cecs.anu.edu.au/u7760022/gp-24s1/-/tree/20667e8b702d408e22d3e01be223a798e5a0aa34
  - related java class [SearchActivity]
- Refactor gits:https://gitlab.cecs.anu.edu.au/u7760022/gp-24s1/-/tree/c2cfcce780541d10b256729258290589dca672fb
  - related java class [MainActivity.java],[SearchFragment.java]
- Solution outline: First, create a main activity to store the navigation bar and fragments. Then change the type of needed activyty,
- at first, search activity was changed to fragment. Later, new fragment of profile, notification were added.

 <hr>

## Summary of Known Errors and Bugs

*[Where are the known errors and bugs? What consequences might they lead to?]*
*List all the known errors and bugs here. If we find bugs/errors that your team does not know of, it shows that your testing is not thorough.*

*Here is an example:*

1. *Bug 1:*
    - *A space bar (' ') in the sign in email will crash the application.*
    - ...

2. *Bug 2:*

3. ...

 <hr>


## Testing Summary

*[What features have you tested? What is your testing coverage?]*
*Please provide some screenshots of your testing summary, showing the achieved testing coverage. Feel free to provide further details on your tests.*

*Here is an example:*

1. Tests for Search
    - Code: [TokenizerTest Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java) for the [Tokenizer Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43)
    - *Number of test cases: ...*
    - *Code coverage: ...*
    - *Types of tests created and descriptions: ...*

2. Tests for read Data-Formats
    - Code: [DataFormatTest.java](app/src/androidTest/java/anu/cookcompass/DataFormatTest.java) for the 
    - [ThemeColor.java](app/src/main/java/anu/cookcompass/theme/ThemeColor.java)
    - - *Code coverage: 100 % *
    - * Number of test cases: 4 * 
    - *Types of tests created and descriptions: 
        - testReadCsv(): Tests reading theme data from a CSV file with randomly generated content. 
      Asserts that the theme list read from the file matches the expected theme names.
        - testReadTxt(): Tests reading the theme color from a text file with randomly generated content. 
      Asserts that the loaded theme color matches the content of the text file.
        - testFixedCsvContent(): Tests reading theme data from a CSV file with fixed content. 
      Asserts that the theme list read from the file matches the expected theme names and does not match unexpected names.
        - testFixedTxtContent(): Tests reading the theme color from a text file with fixed content. 
      Asserts that the loaded theme color matches the expected color and does not match an unexpected color.*

<hr>


## Team Management

### Meetings Records

- [Pre-Meeting](meeting/03-30.md)
- [Team Meeting 1](meeting/04-14.md)
- [Team Meeting 2](meeting/04-21.md)
- [Team Meeting 3](meeting/04-28.md)
- [Team Meeting 4](meeting/05-01.md)
- [Team Meeting 5](meeting/05-05.md)
- [Team Meeting 6](meeting/05-06.md)
- [Team Meeting 7](meeting/05-08.md)

<hr>

### Conflict Resolution Protocol

Our team is divided into two subdivisions, with 3 people developing backend algorithms and 2 people developing frontend pages.
Each subdivision has its own leader.

When a conflict arises, the first step will be a brief subdivision meeting to assess the conflict.
This brief meeting is to find out the cause of the conflict, determine whether or not it affect the other subdivision's work, and assess the time needed for conflict resolution.

If the conflict is difficult to resolve, the leaders of the subdivisions will have a quick discussion to decide whether a team meeting with all group members is necessary.
An all-member group meeting will only be held if necessary.

Finally, a subdivision/all-member group meeting will be held as appropriate to facilitate clear communication and conflict resolution.

The minutes and outcomes of this meeting will be recorded, and decisions or adjustments to task allocation will be made accordingly.

If a member fails to meet the initial plan and/or deadlines, whether because of sickness or other circumstances, the team will adjust the project schedule and task allocations accordingly.

If the reason of the delay is difficulty in finishing the tasks, team members will work together to help solve the task.

If the reason of the delay is unforeseen circumstances outside of the person's control (like a member getting sick), other team members will tolerate the delay. If we are pressed on time, other team members may help this member with their tasks.

If the reason of the delay is something inexcusable like one's unwillingness to work, then this will be treated like a conflict and will be treated according to the aforementioned conflict resolution measures.
