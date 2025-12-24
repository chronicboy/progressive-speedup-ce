# Progressive Speedup CE

A port of the Progressive Item Speedup Addon for **BTW CE 3.0.0** with enhanced features!

Based on the original mod by [Arminias](https://github.com/BTW-Community/Progressive-Item-Speedup-Addon)

## ✨ Features

- **Progressive Speed Increase**: The longer you continuously craft, the faster time moves
- **Smooth FOV Zoom-Out**: Visual feedback with smooth field-of-view zoom-out as speed increases
- **Automatic Detection**: Works with any progressive crafting items (items that use damage to track progress)
- **Balanced Gameplay**: Saves 50-65% of real-time when crafting progressive items, but enemies also move faster!

## 🎮 How It Works

When you use progressive crafting items (like knitting needles, bone carving tools, etc.), the mod detects the item usage and gradually speeds up the world timer. The longer you craft continuously, the faster time moves - up to 10x speed. When you stop crafting, the timer smoothly returns to normal.

The mod features a smooth FOV zoom-out effect that provides visual feedback as the speed increases, making it easy to see when the speedup is active.

## 📋 Requirements

- **BTW CE 3.0.0**
- **Fabric Loader** >= 0.14.19
- **Minecraft** 1.6.4

## 🔧 Building

1. Clone this repository
2. Run `./gradlew build` (or `gradlew.bat build` on Windows)
3. The built jar will be in `build/libs/progressive-speedup-ce-1.0.0.jar`

**Note**: You will need to set up BTW CE 3.0.0 dependencies. See the [BTW CE example mod](https://github.com/BTW-Community/BTW-gradle-fabric-example-CE-3.0.0) for setup instructions.

## 📦 Installation

1. Download the latest release from the [Releases](https://github.com/chronicboy/progressive-speedup-ce/releases) page
2. Place `progressive-speedup-ce-1.0.0.jar` in your Minecraft `mods` folder
3. Launch the game and enjoy faster crafting!

## 🙏 Credits

- **Original Mod**: [Arminias](https://github.com/BTW-Community/Progressive-Item-Speedup-Addon) - BTW Community
- **BTW CE 3.0.0 Port**: chronicboy
- **BTW CE 3.0.0**: [BTW Community](https://github.com/BTW-Community)

## 📄 License

CC0 1.0 Universal (Public Domain)
