angular.module('indigo').controller('indigoDocumentListCtrl', function ($scope, $filter, $timeout, setDocumentsWeights) {
    console.log('hello from indigo!');

    $scope.documents.$promise.then(function () {
        $scope.documents.sort(function (a, b) {
            var weightDiff = b.document.weight - a.document.weight;
            if(weightDiff != 0)
                return weightDiff;

            else return b.document.id - a.document.id;
        });
    });

    var HAS_GROUP = 'hasGroup';
    var NO_GROUP = 'noGroup';

    $scope.getDocumentWeightClass = function (documents, index) {
        var i = index;

        if (documents[index].document.groupStatus == NO_GROUP)
            return 'no-group';
        if (documents[index].document.groupStatus == HAS_GROUP)
            return 'same-weight-middle';

        function getWeight(documents, index) {
            if (index < 0)
                return Number.MIN_VALUE;
            if (index >= documents.length)
                return Number.MAX_VALUE;
            return documents[index].document.weight;
        }

        if (getWeight(documents, index) == getWeight(documents, index + 1)) {
            if (getWeight(documents, index) == getWeight(documents, index - 1))
                return 'same-weight-middle';
            else
                return 'same-weight-top';
        }
        else if (getWeight(documents, index) == getWeight(documents, index - 1))
            return 'same-weight-bottom';

        return '';
    };

    $scope.reapplyWeight = function (documents, document, separate, originalIndex) {
        if (document.dropindex == null)
            return;

        console.log("reapplying");

        var di = document.dropindex;
        if (separate) {
            if (di > 0) {
                var tmp = documents[di-1].document.weight;

                for (var i = di; i >= 0; --i) {
                    ++documents[i].document.weight;
                }

                for (i = di + 1; i < documents.length; ++i) {
                    --documents[i].document.weight;
                }

                document.weight = tmp;
            }
            else if(documents.length > 1){
                document.weight = documents[di+1].document.weight + 1;
            }
        }
        else if(di > 0){
            if(di == originalIndex && di > 0) {
                document.weight = documents[di-1].document.weight
            }
            else {
                document.weight = documents[di-1].document.weight;
            }
        }


    };

    var clientPosX;
    var originalIndex;

    $scope.sortableOptions = {
        start: function (event, ui) {
            clientPosX = event.originalEvent.pageX;
            originalIndex = ui.item.sortable.index;
            ui.item.sortable.model.document.originalWeight = ui.item.sortable.model.document.weight;
        },
        sort: function (event, ui) {
            console.log(event.originalEvent.pageX - clientPosX);

            if (event.originalEvent.pageX - clientPosX < -20) {
                // console.log(ui.item.sortable.model.document.weight);
                // console.log(ui.item.sortable);
                $timeout(function () {
                    if (ui.item.sortable.model == null)
                        return;

                    ui.item.sortable.model.document.groupStatus = NO_GROUP;
                })
            }
            else if (event.originalEvent.pageX - clientPosX > 15) {
                // console.log(ui.item.sortable.model.document.weight);
                // console.log(ui.item.sortable);
                $timeout(function () {
                    if (ui.item.sortable.model == null)
                        return;
                    ui.item.sortable.model.document.groupStatus = HAS_GROUP;
                    // ui.item.sortable.sourceModel[ui.item.sortable.dropindex].document.noWeight = false;
                    ui.item.sortable.model.document.noWeight = false;
                })
            }


        },
        stop: function (event, ui) {
            ui.item.sortable.model.document.dropindex = ui.item.sortable.dropindex != null ? ui.item.sortable.dropindex : ui.item.sortable.index;

            console.log(ui.item.sortable.dropindex);

            var document = ui.item.sortable.model.document;

            $timeout(function () {
                $scope.reapplyWeight($scope.documents, document, document.groupStatus == NO_GROUP, originalIndex);
                document.groupStatus = null;
                setDocumentsWeights($scope.documents);
            });
        },
        update: function (event, ui) {



            // $(window).off('mousemove');
            // if (ui.item.sortable.model.document.noWeight) {
            //     ui.item.sortable.model.document.weight = ui.item.sortable.sourceModel[ui.item.sortable.dropindex].document.weight;
            //     for (var i = 0; i <= ui.item.sortable.dropindex; ++i) {
            //         ++ui.item.sortable.sourceModel[i].document.weight
            //     }
            // }
        }
    };
})