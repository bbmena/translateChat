export const deleteObjectInArray = function (obj, array, keyName) {
    for(let i = 0; i < array.length; i++){
        if(array[i][keyName] === obj[keyName]){
            array.splice(i, 1);
            break;
        }
    }
};