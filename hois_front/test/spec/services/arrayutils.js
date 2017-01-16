'use strict';

describe('Service: ArrayUtils', function () {

  // load the service's module
  beforeEach(module('hitsaOis'));

  // instantiate service
  var ArrayUtils;
  beforeEach(inject(function (_ArrayUtils_) {
    ArrayUtils = _ArrayUtils_;
  }));

  it('should do something', function () {
    expect(!!ArrayUtils).toBe(true);
  });

});
