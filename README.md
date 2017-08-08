# query.maker.java
Simplified ORM based on hybernate

#Requirements

* Java 1.8

#Examples
```
* instantiate :
QueryMaker queryMaker = QueryMaker.getInstance();
queryMaker.createSession("properties");


* select by id :
Entity entity = queryMaker.select(new Entity).exec("id");


* select one with criteria :
Criteria criteria = new Criteria().addValue("criteria", "value");
Entity entity = queryMaker.select(new Entity()).where(criteria).one();


* select multi with criteria :
Criteria criteria = new Criteria().addValue("criteria", "value");
List<Entity> list = queryMaker.select(new Entity()).where(criteria).exec();


* select multi with criteria and limit :
Criteria criteria = new Criteria().addValue("criteria", "value");
List<Entity> list = queryMaker.select(new Entity()).where(criteria).limit(10).exec();


* select multi with criteria and group :
Criteria criteria = new Criteria().addValue("criteria", "value");
Group group = new Group().addValue("group", null);
List<Entity> list = queryMaker.select(new Entity()).where(criteria).group(group).exec());


* insert :
Input input = new Input().addValue("column", "value);
Entity entity = queryMaker.insert(new Entity()).exec(input);


* update :
Entity entity = queryMaker.select(new Entity).exec("id"); // Select entity to update
Input input = new Input().addValue("column", "value);
Entity entity = queryMaker.update(entity).exec(input);


* delete :
Input input = new Input().addValue("column", "value);
Result result = (Result) queryMaker.delete(new Result()).exec(input);


* disconnect from database :
queryMaker.closeSession();
```
