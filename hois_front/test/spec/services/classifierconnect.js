'use strict';

describe('Service: ClassifierConnect', function () {

  // load the service's module
  beforeEach(module('hitsaOis'));

  // instantiate service
  var ClassifierConnect;
  beforeEach(inject(function (_ClassifierConnect_) {
    ClassifierConnect = _ClassifierConnect_;
  }));

  it('should do something', function () {
    expect(!!ClassifierConnect).toBe(true);
  });

});
