'use strict';

describe('Service: Curriculum', function () {

  // load the service's module
  beforeEach(module('hitsaOis'));

  // instantiate service
  var Curriculum;
  beforeEach(inject(function (_Curriculum_) {
    Curriculum = _Curriculum_;
  }));

  it('should do something', function () {
    expect(!!Curriculum).toBe(true);
  });

});
