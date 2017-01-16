'use strict';

describe('Service: Subject', function () {

  // load the service's module
  beforeEach(module('hitsaOis'));

  // instantiate service
  var Subject;
  beforeEach(inject(function (_Subject_) {
    Subject = _Subject_;
  }));

  it('should do something', function () {
    expect(!!Subject).toBe(true);
  });

});
