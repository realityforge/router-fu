# Router-Fu: A framework agnostic, state producing router 

[![Build Status](https://secure.travis-ci.org/realityforge/router-fu.png?branch=master)](http://travis-ci.org/realityforge/router-fu)
[![codecov](https://codecov.io/gh/realityforge/router-fu/branch/master/graph/badge.svg)](https://codecov.io/gh/realityforge/router-fu)

## What is Router-Fu?

Router-Fu is a modern router for your web application. Traditional routers match on the url and render components,
templates or pages but a Router-Fu router matches on the url and outputs state. The application is able to treat
route state like any other application state.

## The Design

The core component of the system is the `Route`. A `Route` is a named pattern against which urls can be matched
against. The pattern also identifies parameters that are extracted from the url and potentially constraints that
each pattern must be validated against. The `Route` also has the ability to construct a url given a set of
parameters.

A typical route may be represented by `"/:function/:org_context/event/:eventId"` and would extract the `function`,
`org_context` and `eventId` parameters from any url that matches the above.

If a url is matched by a `Route` and the parameters satisfy the constraints then a `RouteMatchCallback` is invoked
on the `Route` that can perform custom behaviour and returns an enum that indicates whether the match was:

* no-match. i.e. Further validation of the parameters identified the route as non-matching.
* terminal. i.e. The match was successful and no more routes need to be matched.
* non-terminal. i.e. The match was successful but continue to attempt to match more routes.

The `Router` is an ordered list of routes. During routing a path is passed to the `Router` that walks through
each `Route` in order, until a terminal route is identified or there are no more routes. For each route matched
a `RouteState` is created that contains the `Route` matched, the parameters extracted from the route and whether
the route was "terminal". The routing method on the `Router` returns a `Location` which contains an ordered
list of `RouteState` instances.

The `Router` also exposes the routes so that application code can generate paths for the route given a set of
parameters. It also provides methods via which the application can request that the location be changed.

The `Location` object is effectively immutable and can easily be passed to other frameworks that have their
own state management tools. In an [`Arez`](https://github.com/realityforge/arez), it would expected that you wrap
the `Location` to make it observable.

# Credit

* [Stock Software](http://www.stocksoftware.com.au/) for providing significant support in building and maintaining
  the library, particularly at it's inception.

* This toolkit drew inspiration from [troch/path-parser](https://github.com/troch/path-parser) and
  [router5](http://router5.github.io/).
