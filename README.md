# 💬🖍️📋 Code Highlight

A plugin for Nagram XF. Inline code in messages (card numbers, commands, codes) gets a soft highlight behind it, the way desktop shows it. One tap still copies it.

## Why I made it

On desktop the inline code in my messages stands out and copies with one click. On my phone it looked like plain text, so I could not tell what was tappable.

## What it changes

* Inline code gets a soft background in the message's own text color, so it fits any theme and both your bubbles and everyone else's.
* Tap to copy is the stock behavior and stays exactly as it was.
* Setting: highlight strength, Soft, Medium or Strong. New messages and reopened chats pick up a change.

## Performance

The inline code spans are swapped once, when a message's text is built. Nothing runs in Python while drawing or scrolling, and drawing only sets one paint field.

## Install

1. Use the plugin build of Nagram XF and turn on the plugin engine (Settings, N Settings, Plugins).
2. Download `code_highlight.plugin` from the latest release.
3. Send it to your Saved Messages, tap it and choose install.

The Java source is in `java/`. The plugin carries the compiled class inline.

## License

MIT

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=firestarter1996/xf-code-highlight&type=Date)](https://star-history.com/#firestarter1996/xf-code-highlight&Date)
