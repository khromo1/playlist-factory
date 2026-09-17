# Streaming Content & UI Factories

Software Design Patterns — Assignment 2 (Factory Method & Abstract Factory), Astana IT University.

## What this is

Same overall theme as Assignment 1 — our group's course project is a music streaming platform, so I picked two things from that domain that fit these two patterns naturally.

**Part A (Factory Method):** creating different types of playable audio content — a `Song` and a `Podcast`. They're both "things you can play," but constructed differently and with different fields, so a factory method per type makes sense instead of one constructor trying to cover both.

**Part B (Abstract Factory):** player UI components that need to stay visually consistent depending on the platform — Mobile vs Desktop. A play button and a progress bar always have to match the same platform style, so instead of creating them separately and risking mismatched styles, one factory produces the whole consistent set.

## Structure

```
src/streaming/
  content/
    AudioContent.java       - Product interface
    Song.java                - Concrete Product
    Podcast.java             - Concrete Product
    ContentFactory.java      - Creator (abstract)
    SongFactory.java         - Concrete Creator
    PodcastFactory.java      - Concrete Creator
  ui/
    PlayButton.java          - Abstract Product
    ProgressBar.java         - Abstract Product
    MobilePlayButton.java    - Concrete Product (Mobile family)
    MobileProgressBar.java   - Concrete Product (Mobile family)
    DesktopPlayButton.java   - Concrete Product (Desktop family)
    DesktopProgressBar.java  - Concrete Product (Desktop family)
    UIFactory.java           - Abstract Factory
    MobileUIFactory.java     - Concrete Factory
    DesktopUIFactory.java    - Concrete Factory
  Client.java                 - demonstrates both patterns
```

## Part A: Factory Method

`AudioContent` defines the one thing every piece of content must be able to do — `play()`. `Song` and `Podcast` implement that differently. Instead of the client deciding which one to build with an `if/else`, each `ContentFactory` subclass already knows what it makes:

```java
ContentFactory songFactory = new SongFactory();
AudioContent song = songFactory.createContent("Blinding Lights", "The Weeknd");
song.play();
```

If a third content type shows up later (an audiobook, say), it's a new `Product` + a new `Concrete Creator` — nothing in `Client` needs to change.

## Part B: Abstract Factory

`UIFactory` declares one creation method per component type (`createPlayButton()`, `createProgressBar()`). `MobileUIFactory` and `DesktopUIFactory` each implement it, and each one is guaranteed to hand back a matching pair — you can't accidentally end up with a mobile button next to a desktop progress bar:

```java
UIFactory factory = new MobileUIFactory();
PlayButton button = factory.createPlayButton();
ProgressBar bar = factory.createProgressBar();
```

The client only ever talks to `UIFactory`, `PlayButton`, and `ProgressBar` — it never writes `new MobilePlayButton()` directly, so it has no idea which platform it's actually building for.

## Running it

```
javac src/streaming/content/*.java src/streaming/ui/*.java src/streaming/Client.java -d out
java -cp out streaming.Client
```

Expected output:
```
Playing song: Blinding Lights by The Weeknd
Playing podcast: Design Patterns 101 hosted by Tech Talks
Rendering compact touch play button
Rendering thin swipeable progress bar
Rendering large clickable play button
Rendering wide draggable progress bar
```

## Clean code choices

**1. Client depends only on abstractions, never concrete classes.**
```java
// Client never does this:
// PlayButton button = new MobilePlayButton();

// it only ever does this:
PlayButton button = factory.createPlayButton();
```
This is what actually makes it possible to add a `TVUIFactory` later without touching `Client` at all.

**2. Data abstraction — behavior over exposed fields.**
`Song` and `Podcast` hide their fields entirely and expose behavior (`play()`) instead of raw getters for every field. There's no `getTitle()`/`getArtist()` pair for outside code to poke at — the object does something, it doesn't just hand over its internals.

**3. Law of Demeter — no chained calls into other objects' internals.**
`Client` calls `factory.createPlayButton()` and then `button.render()` directly — never something like `factory.getConfig().getPlatform().getButtonStyle()`. Each object is only asked to do its own job.

**4. Small, single-purpose classes.**
Every Concrete Product does exactly one thing (`Song` plays a song, `MobilePlayButton` renders itself). Every Concrete Factory does exactly one thing (produce one family). Nothing is trying to also validate, log, or configure on the side.

**5. Consistent family guarantee via one abstraction.**
```java
// without Abstract Factory, this kind of mismatch is possible by accident:
PlayButton button = new MobilePlayButton();
ProgressBar bar = new DesktopProgressBar(); // wrong platform, easy to miss

// with it, one factory call guarantees they match:
UIFactory factory = new MobileUIFactory();
PlayButton button = factory.createPlayButton();
ProgressBar bar = factory.createProgressBar();
```

## Notes

Factory Method handles picking *one* product type; Abstract Factory handles keeping a *whole set* of related products consistent with each other. Using both together here made sense because the streaming app genuinely has both kinds of problem — different content types, and platform-consistent UI.
