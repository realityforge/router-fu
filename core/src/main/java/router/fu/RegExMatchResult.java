package router.fu;

interface RegExMatchResult
{
  int getGroupCount();

  String getGroup( int i );
}
