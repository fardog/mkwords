# mkwords

Get lists of random English words, many as you'd like. _mkwords_ is a small web
application for generating word lists in your browser, and its web server also
stands up a single API endpoint that you can request words from.

See the magic yourself by visiting https://mkwords.fardog.io or curl the API
endpoint:

```
$ curl https://mkwords.fardog.io/api/v1/generate

{
  "ok": true,
  "result": [
    "silvered",
    "whalers",
    "rapidly",
    "seaplane"
  ],
  "candidate-count": 70806
}
```

See the [website][] for further examples or to find options for the API; this
README is about getting _mkwords_ running in your own environment.

If you just want the core functionality (random word lists) for your
application, look no further than:

- [hazard][], the Clojure/ClojureScript library used in this project.
- [xkcd-password][], a Node.js library with the same functionality.

## Running

Running this project assumes you have a working Clojure and Leiningen, and also
[Node.js][node], preferably v4.2.x. Node is used to build the SCSS, as I haven't
found a good library for Clojure that doesn't require some form of Ruby.

_mkwords_ is a Clojure/ClojureScript project built using the [Reagent][]
library. It was generated with a `lein new reagent mkpass` and not modified
much, so most of its docs apply here.

First, clone the repo recursively (there is a submodule for the styles):

```
git clone git@github.com:fardog/mkwords.git --recursive
```

Before getting started, install the node dependencies with an `npm install` from
the root of the project.

To get things running in a dev environment, open two terminals and run one
command in each:

```bash
lein run
lein figwheel
```

That'll get you a livereloaded server at http://localhost:3000. **Note:** CSS
will be built from the source SASS when `lein run` is done, but subsequent
changes must be done manually; the easiest way is with a `npm run compile`.

## Building

To build an uberjar ready for distribution, run `lein uberjar`.

Also included is a shell script that builds a docker-deployable image; run
`./build-docker-image.sh` on any *nix system, and you'll build an image suitable
for deployment with Docker. The Docker image includes the uberjar only, so if
you need anything else for your environment the built-in Dockerfile will not
works for you.

## License

Copyright © 2015 Nathan Wittstock

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[node]: http://nodejs.org
[Reagent]: https://github.com/reagent-project/reagent
[hazard]: https://clojars.org/hazard/
[xkcd-password]: https://npm.im/xkcd-password/
[website]: https://mkwords.fardog.io/
