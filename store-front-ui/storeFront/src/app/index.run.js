(function() {
  'use strict';

  angular
    .module('storeFront')
    .run(runBlock, authenticationStoreF);

  /** @ngInject */

  function authenticationStoreF($rootScope, $location, $state, LoginService) {
    // $rootScope.$on('$stateChangeStart',function(){
    //     //  console.log('Changed state to: ' + toState);
    //   });

      if(!LoginService.isAuthenticated()) {
        $state.transitionTo('login');
      }
  }
  function runBlock($log) {

    $log.debug('runBlock end');
  }

})();
