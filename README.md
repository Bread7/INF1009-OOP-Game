# inf1009-team30-2023

```
├───com
│   └───mygdx
│       └───game
│               MyGdxGame.java
├───Entity
│       └───Characters
│               Actor.java              // Parent class
│               Boss.java               // Boss for player to fight, non controllable
│               Player.java             // Player to fight boss, controllable
│       └───Objects
│               GameObject.java         // Objects to render on the map
│               Static.java             // Objects that cannot move
│               Brick.java
│               Podium.java
│               Item.java               // Objects that may move and can be destroyed if collided
│               HealthFood.java
│               UnhealthyFood.java
│
├───managers
│       Audio.java                      // Methods has to be define for audio
│       AudioManager.java               // Manage audio
│       GameScreenManager.java          // Switching between different screens
│       GenericAssetsManager.java       // Manage assets such as texture, skins, button etc
│
├───Screens
│       BaseScreen.java
│       GameOverScreen.java             // Game Over
│       GameScreen.java                 // Actual game world screen
│       HelpScreen.java                 // Display the help screen on how to play the game
│       MenuScreen.java                 // Display the Menu
│       SettingScreen.java              // Change user bind key, like movement or music volumn or screen size.
│
└───utils
        B2DBodyBuilder.java             //testing purpose
        B2DConstants.java               // constant variable 
        Hud.java                        //game screen HUD with Score, timer CountDown, Health(?), world name
        Settings.java                   // Default User key or Music volume. Maybe Use libgdx peference to store in future.
```
