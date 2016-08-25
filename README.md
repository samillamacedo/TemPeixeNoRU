# TemPeixeNoRU 
Is an App developed for Android to inform students of the [University Federal of ABC] (http://www.ufabc.edu.br/), which the menu served in the university restaurant during the week. 

The Universidade Federal do ABC, has campuses in the cities of Santo Andre and Sao Bernardo do Campo, both located in the state of São Paulo in Brazil. At the university are taught graduation, master's degree and PhD programs in the exact sciences and humanities as Engineering, Computer Science, Physics, International Relations and Public Policy.

The application name "Tem Peixe no RU" means a question "Has fish in the University restaurant?". It is a common question among students before they enter the restaurant because the fish is the dish less approved between meals.

### What TemPeixeNoRU does
The menu of the university restaurant has *pdf format* and can be downloaded from the university website. The app has a server that downloads the menu at the university site, parsing to JSON and upload it, so the JSON file can be accessed by the mobile app.
Therefore, the app download the JSON and shows the menu in a cool user interface.

### Layout
![Image of Layout](https://github.com/samillamacedo/TemPeixeNoRU/blob/master/TPNRU_Telas_telas.png)

### Classes and Funcionality
* `MainActivity`: It's the application's core. It binds events, and wraps code together. Initializes Adapters, Views and Listeners.
* `AppSettings`: A class that is used to abstract the low-level code that persists settings data. Is used to save user settings into the default `SharedPreferences`, and provides easy methods for it, such as: `setVegetarian`, `getVegetarian`, `setNotifyLunch`, `getNotifyLunch`...
* `CacheController`: Responsible for fetching data from the Server, and caching that data into files, in order to work even when offline. It provides with a custom listener, called `UpdateCacheListener`, witch can be used to receive low level notifications of network fetch status (start/end/error). It does handles `JSON` parsing, and also memory caching it to avoid multiple useless file reads.
* `CardapioTabsAdapter`: Binds the available days from the cardapio to the Tabs and Fragments. It builds up Fragments, and titles and keep tracks of changes into the model to update the views.
* `CardapioFragment`: Creates and inflates the layout for each Tab Fragment. It also create a unique adapter for each day after converting meal items from `JSONArray` to `ArrayList`.
* `MenuListAdapter`: A custom List Adaptor that renders the list of Grouped meals and it's items. Also, it inflates and create layouts on the go, as well as hidding/binding events to the Info button (when pressed, shows more information about special food items)
* `CustomTabStrip`: A custom View that was made necessary in order to implement the Tab strip with TWO lines. The view extended `PagerTabStrip`, and using some "workarounds", it could re-enable the possibility to have multiple lines.
* `AboutActivity`: An activity to provide users, an overview of how and who developed the app.

### Faced Challenges
During development, a few tasks were hard to get by, mostly because of complications on the complexity of algorithms to improve the UX. Here is a list of them:

* The worst challenge was to parse the menu into a file format that can be used in programming (JSON). The PDF file format is not good to deal with text, as it breaks them into small parts (and even letters). Text processing, was done using many algorithms together, to improve the accuraccy of the parsed menu.
* Another thing that took us two day to solve, was to place two lines instead of one in the `TabStrip`. We found out that no one had done this before, and were the first ones to solve it.
 

### Beyond the final frontier
Things that were necessary, beyond what was seen in class:

* Firebase, used for push-notification
* PDF format definition, and how to parse it from scratch
* Compare dates to find out witch one is the closest one (when users opens up the app, it's default is to the current day, or, the closest day available)
* Cache resources into files, and Memory (a two step caching)
* Recursive Listing (We used a TabsAdapter, to show Lists of Lists). That took us to a 3rd level of recursivity.
* Custom Listeners, to interface with the `CacheController`
