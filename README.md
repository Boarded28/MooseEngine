### MooseEngine
A fixed-timestep Java game/simulation framework, built from scratch by one person, starting from nothing but a loop.

***

**Why this exists**

I keep making simulations and small games. Mazes, cellular automata, raycasters, visualizers,
whatever idea shows up that week. And every time I was rewriting the same scaffolding: set up the window, 
set up the loop, wire the timing, then finally get to the part I actually cared about.

I looked at Love2D and Processing and liked the shape of it. Type the built-in function, write your logic,
then let the engine handle the rest. So instead of doing that setup dance one more time,
I decided to just build the thing that does it for me.

That's MooseEngine.

***

### How to Install?
Get the latest version by downloading the `.jar` file through  [![Latest Release](https://img.shields.io/github/v/release/Boarded28/MooseEngine)](https://github.com/Boarded28/MooseEngine/releases/latest)

`Example: moose-engine-0-1-0-alpha.jar`

***

### How to use?
Import `Nexus` _(the core API)_ from `com.mooseengine.core`, and implement it in your game class. Nexus gives you `update(float delta)`, override it with your game logic.
```java
import com.mooseengine.core.Nexus;

public class MyGame implements Nexus {
    @Override
    public void update(float delta) {
        // your logic here
    }
}
```
Then launch it. `MooseLauncher.launch()` takes your game instance and drives the loop from there.
```java
import com.mooseengine.core.MooseLauncher;

public static void main(String[] args) {
    MooseLauncher.launch(new MyGame());
}
```
That's the whole boilerplate, about 6 lines to get a running loop.
***

**What's not here yet?**
* Rendering pipeline
* Input handling
* Asset loading

Core had to be right before anything gets layered on top of it. Rendering's next.

***

**Who's building this?**

One person. A second-year CS student, building this solo mostly to actually understand the systems I keep using instead of
just consuming them. No team, No deadline. If you're reading this early, then you're seeing it at the very start.