'use strict';

describe('Service: QueryUtils', function () {

  // load the service's module
  beforeEach(module('hitsaOis'));

  // instantiate service
  var QueryUtils;
  beforeEach(inject(function (_QueryUtils_) {
    QueryUtils = _QueryUtils_;
  }));

  it('should do something', function () {
    expect(!!QueryUtils).toBe(true);
  });

});
