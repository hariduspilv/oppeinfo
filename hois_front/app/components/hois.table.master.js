(function() {
  'use strict';

  angular
    .module('hitsaOis')
    .directive('hoisTableRow', HoisTableRow)
    .directive('hoisTableData', HoisTableData);

  /**
   * @description
   * Creates additional row to hold items in hois-table-data-detail/between.
   * In case if there is nothing to hold then row is not created
   * In case if hois-table-data is not used then it will compile it as a default `td` tag.
   *
   * @example
   *  <tr md-row hois-table-row ng-repeat="row in record.students" ng-class-odd="'odd'" ng-class-even="'even'">
   *    <td md-cell>{{$index + 1}}.</td>
   *    <...>
   *    <td md-cell hois-table-data>
   *      <hois-table-data-master>
   *        <md-datepicker ng-model="row.nominalStudyEnd" name="students[{{$index}}].nominalStudyEnd"></md-datepicker>
   *      </hois-table-data-master>
   *      <hois-table-data-detail>
   *        <div class="invalid" ng-show="directiveForm['students['+$index+'].nominalStudyEnd'].$error.required">
   *          {{'main.required' | translate}}
   *        </div>
   *      </hois-table-data-detail>
   *    </td>
   *    <...>
   *  </tr>
   */
  HoisTableRow.$inject = [];
  function HoisTableRow() {
    var directive = {
        bindToController: true,
        controller: HoisTableRowController,
        controllerAs: 'ctrl',
        link: link,
        priority: 50,
        restrict: 'A',
        scope: {
        },
        transclude: true,
        replace: true,
        template: '<tr class="hois-table-row" ng-transclude></tr>'
    };
    return directive;

    function link(_scope, element, attrs, ctrl) {
      if (typeof attrs.excludeClass === 'string' && attrs.excludeClass !== '') {
        ctrl.excludeClass = attrs.excludeClass.split(' ');
      }

      ctrl.orderInit();

      /**
       * Redraw needs to be executed in case if there is decreased amount of columns.
       * If column has ngIf directive then on true value it will initialize (controller and scope of directive) hoisTableData.
       * But on false value it will not trigger anything. So we have to check it somehow.
       * Maybe any better idea?
       *
       * @since 10.08.2020
       * MutationObserver helps to check childList. $watch cannot help in this situation
       */
      var obs = new MutationObserver(redrawIfNeeds);
      obs.observe(element[0], {
        childList: true
      });
      function redrawIfNeeds() {
        if (ctrl.data && ctrl.data.length !== element[0].querySelectorAll('td').length) {
          ctrl.orderRedraw();
        }
      }
    }
  }

  /* @ngInject */
  function HoisTableRowController ($scope, $element, $timeout) {
    var self = this;

    self._init = false;
    self._elementMaster = $element;
    self._elementBetween = undefined;
    self._elementDetail = undefined;
    self.data = [];

    self.init = init;
    self.draw = drawRows;
    self.orderInit = orderInit;
    self.orderRedraw = orderRedraw;

    function init(force) {
      if (self._init && !force) {
        return;
      }

      self.data = Array.prototype.slice.call(self._elementMaster[0].querySelectorAll('td'))
        .map(function (el, idx) {
          var controller = angular.element(el).controller('hoisTableData');
          return {
            index: idx,
            element: el,
            ctrl: controller,
            between: controller ? controller.getBetween() : undefined,
            detail: controller ? controller.getDetail() : undefined
          };
        }
      );

      self._init = true;

      if (force) {
        return;
      }

      var obs = new MutationObserver(updateClasses);
      obs.observe(self._elementMaster[0], {
        attributes: true,
        attributeFilter: ['class']
      });

      // Copy styles from main element (tr.master) to subelements (tr.between and tr.detail)
      // $scope.$watch(function () { return self._elementMaster.attr('class'); });
      function updateClasses() {
        var split = self._elementMaster[0].className.split(' ');
        (self.excludeClass || []).forEach(function (r) {
          for (var i = 0; i < split.length; i++) {
            if (split[i] === r) {
              split.splice(i, 1);
              i--;
            }
          }
        });
        if (self._elementBetween) {
          var betweenClasses = self._elementBetween[0].classList;
          for (var i = betweenClasses.length; i > 0; i--) {
            self._elementBetween.removeClass(betweenClasses[0]);
          }
        }
        if (self._elementDetail) {
          var detailClasses = self._elementDetail[0].classList;
          for (var j = detailClasses.length; j > 0; j--) {
            self._elementDetail.removeClass(detailClasses[0]);
          }
        }
        split.forEach(function (r) {
          if (self._elementBetween) {
            self._elementBetween.addClass(r);
            self._elementBetween.addClass('hois-table-row-between');
          }
          if (self._elementDetail) {
            self._elementDetail.addClass(r);
            self._elementDetail.addClass('hois-table-row-detail');
          }
        });
      }
    }

    function fillRow(tr, source, maxTD, additionalClass) {
      if (typeof maxTD !== 'number' || isNaN(maxTD)) {
        throw new Error('Maximum td element number should be declared to create a row!');
      }
      if (!tr) {
        // Creates a row.
        tr = angular.element(document.createElement('tr'));
        // forEach not available for IE11
        for (var k = 0; k < self._elementMaster[0].classList.length; k++) {
          tr.addClass(self._elementMaster[0].classList[k]);
        }
        if (additionalClass) {
          tr.addClass(additionalClass);
        }
        self._elementMaster.after(tr);
      }
      // New indexed elements
      var mapped = (source || []).reduce(function (obj, r) {
        obj[r.index] = r;
        return obj;
      }, {});
      // Elements which should be rearranged (if we delete them then ngMessages will lose it listener and be ignored)
      var oldToNew = {};
      for (var j = 0; j < tr.children().length; j++) {
        for (var key in mapped) {
          if (tr.children()[j] === mapped[key].element[0]) {
            oldToNew[j] = mapped[key];
          }
        }
      }
      for (var i = 0; i < maxTD; i++) {
        if (tr.children()[i]) {
          if (oldToNew[i]) {
            // position already accured or it was moved already
            if (oldToNew[i].index <= i) {
              continue;
            }
            if (mapped[i]) {
              angular.element(tr.children()[i]).after(mapped[i].element);
            } else {
              angular.element(tr.children()[i]).after(angular.element(document.createElement('td')));
            }
            tr.append(oldToNew[i].element); // place into the last position. It should be fine as after should be placed normally.
          } else {
            if (mapped[i]) {
              angular.element(tr.children()[i]).after(mapped[i].element);
              angular.element(tr.children()[i]).remove();
            } else {
              angular.element(tr.children()[i]).after(angular.element(document.createElement('td')));
              angular.element(tr.children()[i]).remove();
            }
          }
        } else {
          if (mapped[i]) {
            tr.append(mapped[i].element);
          } else {
            tr.append(angular.element(document.createElement('td')));
          }
        }
      }
      // Remove elements after maximum (leftovers which are not used)
      for (var l = maxTD; l < tr.children().length;) {
        tr.children()[l].remove();
      }
      return tr;
    }

    function drawRows(oldData, newData) {
      var betweens = [];
      var details = [];

      newData.forEach(function (r) {
        var oldIndex;

        if (oldData) {
          for (var i = 0; i < oldData.length; i++) {
            if (oldData[i].element === r.element) {
              oldIndex = oldData[i].index;
              break;
            }
          }
        }

        if (r.between) {
          betweens.push({
            index: r.index,
            oldIndex: oldIndex,
            element: r.ctrl.getBetween()
          });
        }
        if (r.detail) {
          details.push({
            index: r.index,
            oldIndex: oldIndex,
            element: r.ctrl.getDetail()
          });
        }
      });

      if (betweens.length > 0) {
        self._elementBetween = fillRow(self._elementBetween, betweens, self.data.length, 'hois-table-row-between');
      } else if (self._elementBetween) {
        self._elementBetween.remove();
        self._elementBetween = undefined;
      }

      if (details.length > 0) {
        self._elementDetail = fillRow(self._elementDetail, details, self.data.length, 'hois-table-row-detail');
      } else if (self._elementDetail) {
        self._elementDetail.remove();
        self._elementDetail = undefined;
      }
    }

    // Redraw/init should be ordered, but not called everytime.
    // During ngIf changing it can call several times this function.
    // Should be limited.

    var ordered = false;
    function orderInit() {
      if (ordered) {
        return;
      }
      ordered = true;
      $timeout(function () {
        init();
        drawRows(undefined, self.data);
        ordered = false;
      });
    }

    function orderRedraw() {
      if (ordered) {
        return;
      }
      ordered = true;
      $timeout(function () {
        var copy = [];
        for (var i = 0; i < self.data.length; i++) {
          copy[i] = self.data[i];
        }
        init(true);
        drawRows(copy, self.data);
        ordered = false;
      });
    }
  }


  HoisTableData.$inject = [];
  function HoisTableData() {
    var directive = {
        bindToController: true,
        controller: HoisTableDataController,
        controllerAs: 'ctrl',
        link: link,
        priority: 50.1,
        restrict: 'A',
        require: ['hoisTableData', '^hoisTableRow'],
        replace: true,
        scope: {
        },
        transclude: {
          master: "hoisTableDataMaster",
          between: "?hoisTableDataBetween", // in case if we need row between master and detail
          detail: "?hoisTableDataDetail"
        },
        template: '<td class="hois-table-data" ng-transclude="master"></td>'
    };
    return directive;

    function link(scope, _element, _attrs, ctrl, transclude) {
      var tdCtrl = ctrl[0];
      var trCtrl = ctrl[1];

      // Using scope.$parent we are giving parent parameters (at least from ngRepeat) to be used inside these elements (detail and between)
      transclude(scope.$parent, function (between) {
        tdCtrl._between = between.wrap(angular.element(document.createElement('td'))).parent();
        tdCtrl._between.addClass('hois-table-data-between');
      }, undefined, 'between');
      transclude(scope.$parent, function (detail) {
        tdCtrl._detail = detail.wrap(angular.element(document.createElement('td'))).parent();
        tdCtrl._detail.addClass('hois-table-data-detail');
      }, undefined, 'detail');

      if (trCtrl && trCtrl._init) {
        trCtrl.orderRedraw();
      }
    }
  }

  /* @ngInject */
  function HoisTableDataController () {
    var self = this;
    self.getBetween = getBetween;
    self.getDetail = getDetail;

    function getBetween() {
      return self._between;
    }

    function getDetail() {
      return self._detail;
    }
  }
})();
