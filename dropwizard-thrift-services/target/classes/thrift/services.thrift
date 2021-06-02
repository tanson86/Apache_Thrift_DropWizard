namespace java com.dropwizard.thrift.services
/**
 * Thrift lets you do typedefs to get pretty names for your types. Standard
 * C style here.
 */
typedef i32 TInteger

/**
 * You can define enums, which are just 32 bit integers. Values are optional
 * and start at 1 if not supplied, C style again.
 */
enum Operation {
  ADD = 1,
  SUBTRACT = 2,
  MULTIPLY = 3,
  DIVIDE = 4
}

/**
 * Structs are the basic complex data structures. They are comprised of fields
 * which each have an integer identifier, a type, a symbolic name, and an
 * optional default value.
 *
 * Fields can be declared "optional", which ensures they will not be included
 * in the serialized output if they aren't set.  Note that this requires some
 * manual management in some languages.
 */
struct Work {
  1: TInteger num1 = 0,
  2: TInteger num2,
  3: Operation op,
  4: optional string comment,
}


/**
 * Structs can also be exceptions, if they are nasty.
 */
exception InvalidOperation {
  1: TInteger whatOp,
  2: string why
}


service Calculator{
  string ping(),
  TInteger calculate(1:Work w) throws (1:InvalidOperation ouch)
}