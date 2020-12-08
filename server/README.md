# Welcome to Sparta Feed Consumer test!

## Usage

To build:

```
$ ./gradlew build
```

To run

```
$ ./gradlew bootRun
```

To run tests (with coverage)

```
$ ./gradlew test
```

The rest report can be found after execution of tests on:
`build/reports/tests/test/index.html`

The JaCoCo coverage report can be found on
`build/reports/jacoco/test/html/index.html`

## Considerations
If the app would need to offer endpoints that retrieves Record or Sensor information, it would be necessary to create output DTOs to not expose the domain.
