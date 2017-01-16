'use strict';

describe('Service: dialogService', function () {

  // load the service's module
  beforeEach(module('hitsaOis'));

  // instantiate service
  var dialogService;
  beforeEach(inject(function (_dialogService_) {
    dialogService = _dialogService_;
  }));

  it('should do something', function () {
    expect(!!dialogService).toBe(true);
  });

});
