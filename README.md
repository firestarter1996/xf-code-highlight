# 💬🖍️📋 Code Highlight

A plugin for Nagram XF. Inline code in messages (card numbers, commands, codes) is drawn in your theme's link color and underlined, on the same background as the rest of the message. It looks tappable because it is: one tap still copies it. Code blocks keep their accent line and COPY CODE button without a separate shade.

## Why I made it

On desktop the inline code in my messages stands out and copies with one click. On my phone it looked like plain text, so I could not tell what was tappable. A grey shade behind it was not enough of a hint, so since 1.1.0 it also looks like a link.

## What it changes

* Inline code takes the link color of your current theme, read while it draws, so every theme works. Messages from others use the incoming link color and your own bubbles use the outgoing one (some themes make that white on a colored bubble).
* Inline code is underlined.
* Inline code sits on the message's own background (since 1.2.0). A soft, medium or strong highlight is still an option.
* Code blocks drop their separate shaded box and sit on the message background too; the accent line, monospace text and COPY CODE button stay. A switch turns this off.
* Tap to copy is the stock behavior and stays exactly as it was.
* Settings: highlight None (default), Soft, Medium or Strong; code blocks on the message background, on or off. New messages and reopened chats pick up a change.

## Performance

The inline code spans are swapped once, when a message's text is built. For code blocks, one number compare runs when the message cell sets up a reply, quote or code line, and only a code line's background value is cleared. Drawing reads one theme color and sets three paint fields. On a Pixel 6 Pro the frame times while I scrolled and switched chats were the same as with 1.0.0. If the theme color cannot be read, the code keeps its stock look.

## Install

1. Use the plugin build of Nagram XF and turn on the plugin engine (Settings, N Settings, Plugins).
2. Download `code_highlight.plugin` from the latest release.
3. Send it to your Saved Messages, tap it and choose install.

The Java source is in `java/`. The plugin carries the compiled class inline.

## License

MIT

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=firestarter1996/xf-code-highlight&type=Date)](https://star-history.com/#firestarter1996/xf-code-highlight&Date)
