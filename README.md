# RecyclerViewFragment
Android library to help to build easily a layout using a RecyclerView widget provided by a RecyclerViewFragment.

Download
========
Via Gradle:
```groovy
// Very soon available.
```
Sample
======
Check out _**/app**_ for a full working sample.

How to use
==========
1. Make sure your fragment extends *RecyclerViewFragment* and call `setRecyclerAdapter()` method in `onViewCreated()`, e.g., and that should do all the work.
   ```java
   public class YourFragment extends RecyclerViewFragment {
   
      @Override
      public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerAdapter(new YourAdapter());
      }
   }
   ```
2. Optionally you can call `setLayoutManager()` to define your RecyclerView's layout manager. If not called, a vertical layout manager will be assumed by default.

3. You can also override `onRecyclerItemClick()` to have item click feedback.

These instructions are enought to create a simple *RecyclerView* layout. If you want to inflate your own custom layout with a *RecyclerView* on `onCreateView()` just make sure your recycler view widget has the id `android:id="@id/recycler"`.

TODO
====
- Improve samples app with more useful samples and screenshots;
- Add progress indicator while loading adapter data;
- Provide easy way to animate items.

Feedback
========
Feel free to report bugs and suggest some improvements or new features.

License
=======

    Copyright (C) 2015 Eduardo Pereira

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
