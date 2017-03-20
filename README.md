# Week 2 Project - *NY Times Explorer*

NY Times Explorer is an android app that allows a user to search for articles on web using simple filters.
The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Note: To run the code you must provide a NYT license key in the property file, /app/src/main/assets/api_key.properties
like "nytimes_api_key=12345678090abcdfe234532".

Time spent: 40 hours spent in total

## User Stories

The following **required** functionality is completed:

* [Y] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [Y] User can click on "settings" which allows selection of **advanced search options** to filter results
* [Y] User can configure advanced search filters such as:
  * [Y] Begin Date (using a date picker)
  * [Y] News desk values (Arts, Fashion & Style, Sports)
  * [Y] Sort order (oldest or newest)
* [Y] Subsequent searches have any filters applied to the search results
* [Y] User can tap on any article in results to view the contents in an embedded browser.
* [Y] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:

* [ ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [Y] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [ ] User can **share an article link** to their friends or email it to themselves
* [ ] Replaced Filter Settings Activity with a lightweight modal overlay

The following **bonus** features are implemented:

* [ ] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the `StaggeredGridLayoutManager` to display improve the grid of image results
* [ ] For different news articles that only have text or only have images, use [Heterogenous Layouts](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) with RecyclerView
* [Y] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [ ] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [ ] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [ ] Uses [retrolambda expressions](http://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks.
* [ ] Leverages the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [ ] Leverages the [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) to access the New York Times API.
* [ ] Replace the embedded `WebView` with [Chrome Custom Tabs](http://guides.codepath.com/android/Chrome-Custom-Tabs) using a custom action button for sharing. (_**2 points**_)

The following **additional** features are implemented:

* [Y] List anything else that you can get done to improve the app functionality!
    ** [Y] Transparent status bar.
    ** [ ] Animated Activity Transitions.  [I ran out of time, so had to remove that code.]

## Video Walkthrough

Here's a walkthrough of implemented user stories:  (http://i.imgur.com/BXf9uF9.gifv)

<img src='http://i.imgur.com/BXf9uF9.gifv' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

* After days of working fine, my ADB server mysteriously stopped finding any emulators to run.  Neither
my Genymotion nor any internal emulator was shown upon querying "adb devices", even after
"adb kill-server" and "adb start-server", and restarting Android Studio, Genymotion, my laptop, etc.
I found several problem reports on StackOverflow of people suffering the same symptoms, but with no responses.
In my case, I finally was able to resolve it by upgrading my Android version 25.4 and my Genymotion
to match that version.  Somehow, I must have lightly accepted an automated Android update which
forced me out of version sync.
* Bug: Via the NYT API, when we request items for any page, the same 10 articles are gotten in results.
 Is this a limitation of the API key I have been given?
* Bug: Via the NYT API, when we request it highlight the search term in results, we see the untranslated
 "<strong></strong>" in the Webview output.
* Bug: The Gridview sometimes unexplicably lays out some articles on the first row beyond the
 scrollbale bounds. Dragging down and causing a relayout works around the UI problem, for now.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright 2017 by David Friedman

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
