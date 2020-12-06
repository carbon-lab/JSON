# JSON
A very lite and powerful JSON parser/serializer implementation.

## Get start
It's easy.

If you want to parse JSON string in java, use: `JSON.parse(json)`.

Similarly, if you want to convert a `JSObject` instance to a JSON string, just 
`JSON.stringify(object)`

## Classes
Here talk about the classes.

Firstly, all the classes you might use in this project is named start with 'JS'. 
And all of them are the sub-class of `JSBase` directly or indirectly.

The `JSObject` is similar to the Java standard class `Map<String, JSBase>` which
use a string object to map other JSON object. But it is NOT a sub-class of `Map`.

The `JSArray` is used to hold the data of the array in JSON. It is similar to 
`Map<Integer, JSBase>`.

Other class is stand for the base type of JSON like number, string, etc.

### JSON
This is a programming interface class. It only contains two functions: `parse`
and `stringify`.

The `parse` function is used to parse a JSON string and returns a `JSObject` object
which could also be a `JSArray` object.

```java
public static JSObject parse(String json)
```

`stringify` functions will returns the JSON string of any `JSObject` instance.

```java
public static String stringify(JSObject object)
```

### JSObject
```java
// Set a field in JSON object
public JSBase put(String key, JSBase value)

// Get a field from JSON object
public JSBase get(String key)

// Get the key-value entry set
public Set<Entry<String, JSBase>> entrySet()
```

### JSArray
```java
// Set s value in the array by number index
public void set(int index, JSBase value)

// Get a value in the array by number index
public JSBase get(int index)

// Get the amount of elements
public int length()

// Append an element to the array end, like push() in JavaScript
public void push(JSBase value)
```
