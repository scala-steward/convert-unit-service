[![Build Status](https://travis-ci.org/felipebonezi/convert-unit-service.svg?branch=main)](https://travis-ci.org/felipebonezi/convert-unit-service) [![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

![convert-unit-service](https://socialify.git.ci/felipebonezi/convert-unit-service/image?description=1&font=KoHo&forks=1&language=1&owner=1&pattern=Charlie%20Brown&pulls=1&stargazers=1&theme=Light)

# Convert Unit Service
A conversion unit service implemented with Scala and Play! Framework 
to perform unit conversion to SI from their “widely used” counterparts. 

The web service has a single endpoint with a single route, Convert Units, 
which will convert any properly formatted unit string to their SI counterparts.

**Request:**
`GET /unit/si?units=(degree/minute)`

**Response**
`{"unit_name": "(rad/s)", "multiplication_factor": 0.00029088820866572}`

## Unit Conversion Factors

This table denotes valid input and conversion factors for you to implement. 
Either values from the left two columns are valid input, as are SI units themselves.
 
| Name  | Symbol  | Quantity  | SI Conversion |
|---|---|---|---|
| minute  | min  | time  | 60s  |
| hour  | h  | time  | 3600s  |
| day      | d  | time  | 86400s  |
| degree   | °  | unitless/plane angle  | (π/180) rad  |
| arcminute  | '  | unitless/plane angle  | (π/10800) rad  |
| arcsecond  | "  | unitless/plane angle  | (π/648000) rad  |
| hectare  | ha  | area  | 10000 m2  |
| litre  | L  | volume  | 0.001 m3  |
| tonne  | t  | mass  | 1000 kg  |

## How to run?

### Create a docker image

* Install sbt (https://www.scala-sbt.org/1.x/docs/Setup.html) on your computer.
* Open sbt shell and run `docker:publishLocal`.
    * You don't need to create your own Dockerfile, Play does it for you!
* Run a docker container: `docker run --rm -p 9000:9000 convert-unit-service`.
* Browse to `http://localhost:9000/unit/si?units=(degree/minute)`.
    * You should see a JSON message!
