# HITSA OIS

## Packages
Angular 1.4.8 (upgrading to 1.5.x or 1.6.x currently has some problems)

## Requirements

For development run `npm install -g grunt-cli bower`
for using yeoman run `npm install -g yo generator-karma generator-angular` 

## Build & development

Run `npm install` for installing project nodejs dependencies.
Run `bower install` for installing bower components
Run `grunt serve` for running application locally.
Run `grunt jshint` for static code analys

## Testing

Running `grunt test` will run the unit tests with karma.

## Build production version

Run `grunt build`

Kopeerida kaust `dist` nginx public kausta alla

##Backend configuration
* set API url base endpoint in config file `app/config.js`

