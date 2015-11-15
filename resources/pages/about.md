# About

_mkwords_ is a word list generator; it generates a random selection of words
from a high-quality default [word list][words].

The source code for _mkwords_ is available on [Github][code].

## Usage

There are two modes of operation; [browser](/) and [API](/api).

### Browser

The browser is where you are now; visiting the [generation](/) page loads a
small application in your browser, which generates a random selection of words
from the default word list.

The words generated are created entirely in your browser, and are never sent
across the internet.

### API

There is also an [API](/api) to which you can make requests and receive
word lists back. These lists are generated on the _mkwords_ server and sent to
you over [HTTPS][https]. 

## Other Versions

[**node-xkcd-password**][npm] is a [Node.js][node] version of this tool, which
also includes a CLI utility that can be used to generate word lists. As a
library you can include it in your Node applications.

[**hazard**][hazard] is the [Clojure/ClojureScript][clojure] version of this
library, and is what powers this site. You can use the [hazard][hazard] library
in your own applications.

[words]: https://raw.githubusercontent.com/fardog/hazard/master/resources/mwords/113809of.fic
[https]: https://en.wikipedia.org/wiki/HTTPS
[code]: https://github.com/fardog/mkwords
[npm]: https://npm.im/xkcd-password
[node]: https://nodejs.org
[hazard]: https://clojars.org/hazard
[clojure]: https://clojure.org
