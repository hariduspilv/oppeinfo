'use strict';

describe('Service: DataUtils', function () {

  // load the service's module
  beforeEach(module('hitsaOis'));

  // instantiate service
  var DataUtils;
  beforeEach(inject(function (_DataUtils_) {
    DataUtils = _DataUtils_;
  }));

  it('should do something', function () {
    expect(!!DataUtils).toBe(true);
  });

});
