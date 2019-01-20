# REST App

A simple application to display a list of movie posters. The posters are
obtained from a JSON response, from a remote system.

When you click on a poster, it invokes a behavior. Currently, this
behavior is to open a Google search for the movie name.

The data modeling and presentation layers are decoupled. Dependencies
are provided through Dagger. Light use of OkHttp and Picasso for network
calls and image loading.

<img src="https://raw.githubusercontent.com/jamesonwilliams/RestApp/master/screenshot.png" alt="REST App Screenshot" width="200">

## The Presentation Layer

An Android `RecyclerView` is used to display a list of posters. Each
item contains an image for the movie, and its name.

The presentation layer is arranged into a "posters list" component, and
a "single poster" component. A poster list manages single posters. Both
are implemented as VIPER modules.

`PosterListActivity` implements the view for the list. `PosterView` is a
`RecyclerView.ViewHolder`, and implements the view for a single poster.
It is the goal of these components to be "passive views," that just call
through to their corresponding `Presenter` implementations.

## The Data Model

A `Poster` is a domain model, of a real-world movie poster.

Data models are defined and implemented in `org.nosemaj.restapp.model`.
`PostersProvider` provides a `List` of `Poster`.

`CachingPostersProvider` implements this contract by querying an
`ApiClient` for data, if there are an insufficient number of `Poster` in
memory. If there are already a sufficient number of posters loaded, the
`CachingPostersProvider` simply returns the cached posters.

`NosemajApiClient` implements the `ApiClient` contract, by performing an
HTTP `GET` against `nosemaj.org`. This API renders a JSON response,
containing a list of posters, using a known JSON schema.

