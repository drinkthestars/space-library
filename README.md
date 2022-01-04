## :milky_way: Space Library

![build](https://github.com/drinkthestars/space-library/actions/workflows/android.yml/badge.svg)

POC of an MVI + VIPER based arch. Search for images courtesy of
the [NASA Images API](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf).

#### The Variants

There are two variants with similar functionality, plus a module that contains common presentation
logic:

0. `common`
    - Contains all the presentation logic common to both variants
    -
   Uses [SnapshotState](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/runtime/runtime/src/commonMain/kotlin/androidx/compose/runtime/SnapshotState.kt;l=17?q=SnapshotSt&sq=&ss=androidx%2Fplatform%2Fframeworks%2Fsupport:compose%2F)
   APIs
   from [androidx.compose.runtime](https://developer.android.com/reference/kotlin/androidx/compose/runtime/package-summary)
3. `astro-androidview`
    - Using `Fragment`s, Android UI Toolkit
      & [Data Binding](https://developer.android.com/topic/libraries/data-binding)
      , [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
      for rendering

2. `astro-compose`
    - Using [Jetpack Compose](https://developer.android.com/jetpack/compose/setup) for rendering

#### References

- Another [similar project](https://github.com/drinkthestars/virtual-date-planner)

#### License

[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](http://opensource.org/licenses/MIT)
