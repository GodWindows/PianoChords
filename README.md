## PianoChords Android Application
![The PianoChord App Icon](app/src/main/res/mipmap-hdpi/ic_launcher.webp)
### Overview

The PianoChords application is an educational tool designed to help users learn and practice piano chords. It cycles through different piano chords, displaying them on the screen in a random order with various customizable options such as the chord duration, minor chords, and the use of flats and sharps.

### Features

* Chord Display: Shows piano chords with main notes, accidentals (# or b), and minor indicators (m).
* Timer: Displays a countdown timer for each chord.
* Customization: Users can customize the chord duration, enable/disable minor chords, flats and sharps, and choose french translation options for chord names.
* Automatic Swiping: Automatically transitions between chords at user-defined intervals.

### Components

* TextViews: Displays the main chord, accidentals, and minor indicators.
* Buttons: Start and reset buttons to control the chord display.
* RadioButtons and RadioGroup: Allows users to select the chord display duration.
* Switches: Toggles for minor chords, flats/sharps, and translation of chord names.

### Installation

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Build the project to resolve dependencies.
4. Run the application on an Android device or emulator.

### Usage

#### Initialization

The app initializes with:

* Minor chords enabled.
* Flats and sharps enabled.
* Translation in French disabled
* The smallest chord duration selected by default.

#### Controls

* Start Button: Begins displaying chords based on the selected settings.
* Reset Button: Stops the chord display and resets the timer and chord text.
* Radio Buttons: Choose the duration between chord displays (5, 10, or 15 seconds).
* Switches:
    * Minor Switch: Enable or disable minor chords.
    * Flat and Sharp Switch: Enable or disable display of flats and sharps.
    * Translation Switch: Translate chord names to French if enabled.

#### Display Logic

The application cycles through a list of chords, displaying each chord for the duration selected by the user.

* A timer counts down the time remaining for each chord, turning red when less than 5 seconds remain.
* Chord text is updated with appropriate accidentals and minor indicators based on user settings.

### Algorithmic Choices & Customization
The min SDK is set to 24. 
The code is written in the most generic way possible to let you fully customize it and adapt it to any similar project.
For example, to customize the delay durations between chords, modify the constants `SMALL_DURATION`, `MEDIUM_DURATION`, and `LARGE_DURATION` in `MainActivity` and just update the corresponding values in the `strings.xml` file.
The app use Android Binding to manipulate the different layout components. `findViewById()` could have been an excellent choice too but since the app may be updated, customized and adapted to contain more components, the use of binding seems to be best choice here.
The Chord's static method `getChords()` use a succession of if-statements to fill the chords array with the required chords. With this architecture, we can easily update the method if we ever want to add more options to the chord
