angular.module('indigo').controller('indigoDocumentListCtrl', function ($scope, $filter, $timeout) {
    console.log('hello from indigo!');

        $scope.documentsMerged = [];
        $scope.documents.$promise.then(function () {
            $scope.documentsMerged = $filter('mergeWeights')($scope.documents);
        });

        var clientPosX;

        $scope.sortableOptions = {
            start: function (event, ui) {
                clientPosX = event.originalEvent.pageX;

                $(window).on('mousemove', function (event) {
                    console.log(event.originalEvent.pageX - clientPosX);

                    if(event.originalEvent.pageX - clientPosX < -20) {
                        // console.log(ui.item.sortable.model.document.weight);
                        // console.log(ui.item.sortable);
                        $timeout(function () {
                            if(ui.item.sortable.model == null)
                                return;

                            ui.item.sortable.model.document.noWeight = true;
                        })
                    }
                    if(event.originalEvent.pageX - clientPosX > 15) {
                        // console.log(ui.item.sortable.model.document.weight);
                        // console.log(ui.item.sortable);
                        $timeout(function () {
                            if(ui.item.sortable.model == null)
                                return;
                            console.log(ui.item.sortable.dropindex);
                            // ui.item.sortable.sourceModel[ui.item.sortable.dropindex].document.noWeight = false;
                            ui.item.sortable.model.document.noWeight = false;
                        })
                    }
                })
            },
            stop: function (event, ui) {
                $(window).off('mousemove');
            },
            update: function (event, ui) {
                $(window).off('mousemove');
                if(ui.item.sortable.model.document.noWeight) {
                    ui.item.sortable.model.document.weight = ui.item.sortable.sourceModel[ui.item.sortable.dropindex].document.weight;
                    for(var i = 0; i <= ui.item.sortable.dropindex; ++i) {
                        ++ui.item.sortable.sourceModel[i].document.weight
                    }
                }
            }
        };
    })
    .filter('mergeWeights', function () {
        return function(list) {
            if(list == null || list.length == 0) {
                return [];
            }
            var lastWeight = list[0].document.weight;
            var lastGroup = {'weight': lastWeight, 'documents': [list[0].document]};
            var merged = [lastGroup];

            for(var i = 1; i < list.length; ++i) {
                var document = list[i].document;

                if(document.weight == lastWeight) {
                    lastGroup.documents.push(document);
                }
                else {
                    lastGroup = {weight: document.weight, documents: [document]};
                    lastWeight = document.weight;
                    merged.push(lastGroup);
                }
            }

            console.log(merged);
            return merged;
        }
    });